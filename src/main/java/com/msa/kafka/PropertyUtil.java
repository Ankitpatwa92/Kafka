package com.msa.kafka;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

public class PropertyUtil {
	
	
	static Properties getProducerProperties() {
		
	       Properties props = new Properties();
	       props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094,localhost:9095,localhost:9096");
	       props.put(ProducerConfig.ACKS_CONFIG, "all");
	       props.put(ProducerConfig.RETRIES_CONFIG, 0);
	       props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
	       props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
	       return props;
	}

	public static Properties getConsumerProperties() 
	{		
		Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9094,localhost:9095,localhost:9096");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");    
        props.put("enable.auto.commit", "true");
        props.put("session.timeout.ms",20000);        
        props.put("max.poll.records",3);
        props.put("max.partition.fetch.bytes", "1024");
        props.put("heartbeat.interval.ms",3000);
        props.put("max.poll.interval.ms",30000);          
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
	}

}
