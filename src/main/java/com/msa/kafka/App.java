package com.msa.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
                        
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("group.id", "testgroup-5");
        
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
                       
        //props.put("enable.auto.commit", "true");
        //props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");                
        //props.put(ConsumerConfig.CLIENT_ID_CONFIG, "client-2");        
        props.put("max.poll.records",20);
        
        
        // This is how to control number of records being read in each poll
        //props.put("max.partition.fetch.bytes", "135");
        
        //properties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        //consumer.seekToBeginning(consumer.assignment())
        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String,String>(props);
        

        
        List<String> topics = new ArrayList<>();
        
        String topicName="sample-topic";
/*        TopicPartition partition0 = new TopicPartition(topicName, 0);
        TopicPartition partition1 = new TopicPartition(topicName, 1);
        kafkaConsumer.assign(Arrays.asList(partition1));
        kafkaConsumer2.assign(Arrays.asList(partition0));
*/		topics.add(topicName);
        kafkaConsumer.subscribe(topics);
        
        try {            
        	   //Duration duration=Duration.ofMillis(1000);
        	
        	   //kafkaConsumer.poll(0);
        	   //kafkaConsumer.seekToBeginning(kafkaConsumer.assignment());
        	   ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(100);
        	   Set<TopicPartition> part = consumerRecords.partitions();        	           	   
        	   System.out.println(consumerRecords.count());
               consumerRecords.forEach(record -> 
                    System.out.println(String.format("Topic - %s, Partition - %d, Value: %s, Offset: %d, key: %s", record.topic(), record.partition(), record.value(),record.offset(),record.key()))
                );
               Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
        	kafkaConsumer.close();
        }
        
        
System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Partition2<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	KafkaConsumer<String,String> kafkaConsumer2 = new KafkaConsumer<String,String>(props);
	kafkaConsumer2.subscribe(topics);
        try {
            
     	      ConsumerRecords<String, String> consumerRecords = kafkaConsumer2.poll(100);     	           	           	  
     	      System.out.println(consumerRecords.count());
              consumerRecords.forEach(record -> 
                 System.out.println(String.format("Topic - %s, Partition - %d, Value: %s, Offset: %d, key: %s", record.topic(), record.partition(), record.value(),record.offset(),record.key()))
             );
              Thread.sleep(5000);
     } catch (Exception e) {
         System.out.println(e.getMessage());
     } finally {
         kafkaConsumer2.close();
     }
        
        
    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Partition3<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
     	KafkaConsumer<String,String> kafkaConsumer3 = new KafkaConsumer<String,String>(props);
     	kafkaConsumer3.subscribe(topics);
             try {
                 
          	      ConsumerRecords<String, String> consumerRecords = kafkaConsumer3.poll(100);     	           	           	  
          	      System.out.println(consumerRecords.count());
                   consumerRecords.forEach(record -> 
                      System.out.println(String.format("Topic - %s, Partition - %d, Value: %s, Offset: %d, key: %s", record.topic(), record.partition(), record.value(),record.offset(),record.key()))
                  );
                   Thread.sleep(5000);
          } catch (Exception e) {
              System.out.println(e.getMessage());
          } finally {
              kafkaConsumer3.close();
          }
         }

        
 
        
    
        
        
        
        
    }
    
   