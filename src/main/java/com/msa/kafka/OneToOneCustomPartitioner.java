package com.msa.kafka;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import com.msa.kafka.Bill.Item;

public class OneToOneCustomPartitioner implements Partitioner {


	
	@Override
	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes,
			Cluster cluster) {
		System.out.println("inside partitoner");
		String k=(String) key;		
		if(Item.badam.name().equals(k) || Item.jeans.name().equals(k)) {				
			return 0;
		}else if(Item.kaju.name().equals(k) || Item.shirt.name().equals(k)) {
			return 1;
		}			
		return 2;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
	
}
