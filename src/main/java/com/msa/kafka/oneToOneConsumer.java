package com.msa.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.msa.kafka.Bill.Category;

public class oneToOneConsumer {

	public static void main(String[] args) 
	{
		Properties props = PropertyUtil.getConsumerProperties();
		props.put("group.id","m4");
		try(KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String,String>(props)) {

			String topic1=Category.dryfruit.name();
			List<String> topics = Arrays.asList(new String[]{topic1});
			kafkaConsumer.subscribe(topics);			
			while(true) 
			{				
				Duration duration=Duration.ofMillis(1000);
				ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(duration);
				Iterator<ConsumerRecord<String, String>> iterator = consumerRecords.iterator();							
				while(iterator.hasNext())
				{			
					ConsumerRecord<String, String> record = iterator.next();
					System.out.println(record);
				}
				
			} 
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
