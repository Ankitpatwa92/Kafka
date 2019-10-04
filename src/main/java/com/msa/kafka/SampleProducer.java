package com.msa.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class SampleProducer {

	 public static void main(String[] args) throws InterruptedException{

	       Properties props = new Properties();
	       props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091");
	       props.put(ProducerConfig.ACKS_CONFIG, "all");
	       props.put(ProducerConfig.RETRIES_CONFIG, 0);
	       props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
	       props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
           
	       props.put("security.protocol", "SSL");	       
	       //props.put("ssl.endpoint.identification.algorithm","");  //host name verification
	       //props.put("ssl.truststore.location","D:/Tools/kafka_2.11-2.1.0/kafka.client.truststore.jks");
	       //props.put("ssl.truststore.password","changeit");
	       
	      // props.put("partitioner.class", "com.intellect.kafka.PartitionerCategory");
	       
	        //props.put("acks", "all");
	       //props.put("retries", 0);
	      //props.put("batch.size", 10);
	     //props.put("linger.ms", 1);
	       
	       org.apache.kafka.clients.producer.Producer<String, String> producer = new KafkaProducer<String, String>(props);
	       TestCallback callback = new TestCallback();
	       final String topicName="Best-Topic";
	       for (long i = 0; i < 5 ; i++) {	    	   
	    	   System.out.println("for loop>>>>>>>>>>");
	           ProducerRecord<String, String> data = new ProducerRecord<String, String>(
	        		   topicName,(i+1)+"", "message-"+i+1 );
	           producer.send(data, callback);
	           System.out.println("for loop>>>>>>>>>> end");
	           Thread.sleep(500);
	       }

	       producer.close();
	   }


	   private static class TestCallback implements Callback {
	       @Override
	       public void onCompletion(RecordMetadata recordMetadata, Exception e) {
	           if (e != null) {
	               System.out.println("Error while producing message to topic :" + recordMetadata);
	               e.printStackTrace();
	           } else {
	               String message = String.format("sent message to topic:%s partition:%s  offset:%s", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
	               System.out.println(message);
	           }
	       }
	   }
	

}
