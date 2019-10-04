package com.msa.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;

public class WorkingConsumer {

	
	
	public static void main(String[] args) {
		new WorkingConsumer().consume();
	}
	
	public void consume() {
		
		Properties props=getConfiguration();
		final String topicName="ACCOUNT-BALANCES-INBOUND-TOPIC";
		List<String> topics = Arrays.asList(new String[]{topicName});
		KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String,String>(props);        		                        
        kafkaConsumer.subscribe(topics, new ConsumerRebalanceListener() {
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {	                	
            	
                    System.out.printf("%s topic-partitions are revoked from [THIRD] this consumer\n",Arrays.toString(partitions.toArray()));
            }
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                System.out.printf("%s topic-partitions are assigned to [THIRD] this consumer\n", Arrays.toString(partitions.toArray()));
            }
        });
        
        consumerLoop(kafkaConsumer);		
	}
	
	
	public Properties getConfiguration() {
		Properties props = new Properties();
        props.put("bootstrap.servers", "10.196.11.27:9092");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("group.id", "account-balance-group");
        //props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put("enable.auto.commit", "false");
        props.put("session.timeout.ms", 10000);        
        props.put("max.poll.records",3);
        props.put("heartbeat.interval.ms",1000);        
        props.put("max.poll.interval.ms",30000);
        props.put("max.partition.fetch.bytes", "1024");

        //SSL Settings
        //props.put("security.protocol", "SSL");
        //props.put("ssl.endpoint.identification.algorithm","");
        
        //props.put("auto.commit.interval.ms", 1);
        //request.timeout.ms
        // This is how to control number of records being read in each poll
		return props;  
	}

	

	public void consumerLoop( KafkaConsumer<String,String> kafkaConsumer) {
					

		try {
        	while(true) {

        	   System.out.println("Going to read next message>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        	   Duration duration=Duration.ofMillis(1000);        	   
        	   ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(duration);
                System.out.println("Thread"+Thread.currentThread().getName()+" "+"count=="+consumerRecords.count());
               	 consumerRecords.forEach(record -> {
                 System.out.println(String.format("Thread Name: %s,Topic - %s, Partition - %d, Value: %s, Offset: %d, key: %s",Thread.currentThread().getName(), record.topic(), record.partition(), record.value(),record.offset(),record.key()));
               });
               
			//kafkaConsumer.commitSync();
               kafkaConsumer.commitAsync(new OffsetCommitCallback() {
				
				@Override
				public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
					// TODO Auto-generated method stub
					offsets.forEach((k,v)->{
						System.out.println("commited offset "+v);
					});
				}
			});
               
         }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	kafkaConsumer.close();
        }

	}
	
}
