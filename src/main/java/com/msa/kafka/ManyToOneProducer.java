package com.msa.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.msa.kafka.Bill.Category;
import com.msa.kafka.Bill.Item;

public class ManyToOneProducer {

		
		public static final String almond = "ALMOND";
		public static final String kaju = "KAJU";
		public static final String kismiss = "KISMISS";

		public static final String jeans = "JEANS";
		public static final String shirt = "SHIRT";
		public static final String tshirt = "THISRT";

		
		public static void main(String[] args) {

			ExecutorService exeService = Executors.newFixedThreadPool(3);
			exeService.execute(new ProdRunnable());
			exeService.execute(new ProdRunnable());
			exeService.execute(new ProdRunnable());
			exeService.shutdown();		
		}

		
		public static List<Bill> prepareBillList(){

			List<Bill> billlist=new  ArrayList<>();

			for(int i=0;i<10;i++) {
						
				Bill bill1=new Bill();
				bill1.setCategory(Category.dryfruit);
				bill1.setItem(Item.badam);
				bill1.setDescription("This is"+Item.badam);
				bill1.setTotal(10+i);
		
				Bill bill2=new Bill();
				bill2.setCategory(Category.dryfruit);
				bill2.setItem(Item.kaju);
				bill2.setDescription("This is"+Item.kaju);
				bill2.setTotal(15+i);
		
				Bill bill3=new Bill();
				bill3.setCategory(Category.cloathing);
				bill3.setItem(Item.jeans);
				bill3.setDescription("This is"+Item.jeans);
				bill3.setTotal(21+i);
		
				Bill bill4=new Bill();
				bill4.setCategory(Category.cloathing);
				bill4.setItem(Item.shirt);
				bill4.setDescription("This is"+Item.shirt);
				bill4.setTotal(25+i);
		
				billlist.add(bill1);
				billlist.add(bill2);
				billlist.add(bill3);
				billlist.add(bill4);
			}		
			
			return billlist;
		}

	}


class ProdRunnable implements Runnable {

	@Override
	public void run() {
		
		List<Bill> list = ManyToOneProducer.prepareBillList();
		Properties props = PropertyUtil.getProducerProperties();
		props.put("partitioner.class", "com.msa.kafka.OneToOneCustomPartitioner");
		
		for (Bill bill : list) 
		{					
			try (Producer<String, String> prod = new KafkaProducer<String, String>(props)) {				

				ProducerRecord<String, String> data = new ProducerRecord<String, String>(bill.getCategory().toString(), bill.getItem().toString(),
						bill.getDescription()+"; Total"+bill.getTotal()+" Thread"+Thread.currentThread().getName());
				prod.send(data);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		
	}
	
}
