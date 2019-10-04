package com.msa.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class OneToOneProducer {

	public static final String almond = "ALMOND";
	public static final String kaju = "KAJU";
	public static final String kismiss = "KISMISS";

	public static void main(String[] args) {

		Properties props = PropertyUtil.getProducerProperties();

		props.put("partitioner.class", "com.intellect.kafka.OneToOneCustomPartitioner");

		try (Producer<String, String> prod = new KafkaProducer<String, String>(props)) {

			String topicName = "dry-fruit";

			for (int i = 0; i < 10; i++) {
				ProducerRecord<String, String> data = new ProducerRecord<String, String>(topicName, kaju,
						"This is bill of kaju-" + i);
				prod.send(data);
			}

			for (int i = 0; i < 10; i++) {
				ProducerRecord<String, String> data = new ProducerRecord<String, String>(topicName, almond,
						"This is bill of almond-" + i);
				prod.send(data);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
