package com.msa.kafka;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class MultiProducer {

	 public static void main(String[] args) throws InterruptedException {

        	ExecutorService exService = Executors.newFixedThreadPool(4);
        	for(int i=0;i<100;i++) {
        		exService.execute(new Task(i)); 
        	}	
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
	

	   private static Properties getProducerConfig() {
	       Properties props = new Properties();
	       props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	       props.put(ProducerConfig.ACKS_CONFIG, "all");
	       props.put(ProducerConfig.RETRIES_CONFIG, 0);
	       props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
	       props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
	       return props;
	   }
	   
	   static void produceMessage(String i)  {		   
		 Properties props=getProducerConfig();
		 Producer<String, String> producer = new KafkaProducer<String, String>(props);
		 TestCallback callback = new TestCallback();
		 final String topicName="Kirana";
          ProducerRecord<String, String> data = new ProducerRecord<String, String>(
        		   topicName,"key"+i, "message-"+i+1);
           producer.send(data, callback); 
           producer.close();
	   }
	   
}

class Task implements Runnable {

	int j;
	Task(int i) {
		this.j=i;		
	}
	
	@Override
	public void run() {
		MultiProducer.produceMessage(j+"");
		System.out.println(this.j+" {}  "+Thread.currentThread().getName());
	}		
}
