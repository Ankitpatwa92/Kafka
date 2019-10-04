package com.msa.kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import com.msa.kafka.Bill.Category;

public class ManyConsumerOneTopic {

	public static void main(String[] args) 
	{		
		ExecutorService threadPool = Executors.newFixedThreadPool(3);				
		threadPool.execute(new ConsumerRunnable(0));
		threadPool.execute(new ConsumerRunnable(1));
		threadPool.execute(new ConsumerRunnable(2));
		threadPool.shutdown();
						
	}
}


class ConsumerRunnable implements Runnable {

	int partition;
	ConsumerRunnable(int partition){
		this.partition=partition;
	}
	
	@Override
	public void run() {	
		
		Properties props = PropertyUtil.getConsumerProperties();

   		props.put("group.id","manyToOne-static3");     //user group id with RoundRobin strategy	
		
		  try(KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String,String>(props)) {
			
			partitionStrategyRoundRobin(kafkaConsumer, Category.dryfruit.name());		
			
			//partitionStrategyFixed(kafkaConsumer,Category.dryfruit.name());

			while(true) 
			{				
				Duration duration=Duration.ofMillis(1000);
				ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(duration);
				Iterator<ConsumerRecord<String, String>> iterator = consumerRecords.iterator();							
				while(iterator.hasNext())
				{			
					ConsumerRecord<String, String> record = iterator.next();
					System.out.println("Thread:"+Thread.currentThread().getId()+"  "+record);
				}
				
			} 
		}catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void partitionStrategyRoundRobin(KafkaConsumer<String,String> kafkaConsumer,String topic) {							
		List<String> topics = Arrays.asList(new String[]{topic});
		kafkaConsumer.subscribe(topics);			
	}
	
	public void partitionStrategyFixed(KafkaConsumer<String,String> kafkaConsumer,String topic) {
		
		Collection<TopicPartition> partitions=new ArrayList<TopicPartition>();
		partitions.add(new TopicPartition(topic,partition));
		kafkaConsumer.assign(partitions);

	}
			
}