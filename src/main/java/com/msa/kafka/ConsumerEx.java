package com.msa.kafka;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.time.Duration;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;

public class ConsumerEx {

	

	
	public void consume() {
        
		
  
/*        ExecutorService exService = Executors.newFixedThreadPool(3);
        final String topicName="Test-Topic";
        List<String> topics = Arrays.asList(new String[]{topicName});*/
     /*   
        exService.execute(new Runnable() {			
			@Override
			public void run() {
				Properties props=getConfiguration("first");
		        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String,String>(props);        		                        
		        kafkaConsumer.subscribe(topics, new ConsumerRebalanceListener() {
	                public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
	                    System.out.printf("%s topic-partitions are revoked from [FIRST] this consumer\n", Arrays.toString(partitions.toArray()));
	                }
	                public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
	                    System.out.printf("%s topic-partitions are assigned to [FIRST] this consumer\n", Arrays.toString(partitions.toArray()));
	                }
	            });
		        consumerLoop(kafkaConsumer);

			}
		});
        
        exService.execute(new Runnable() {			
			@Override
			public void run() {				
				Properties props=getConfiguration("second");
		        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String,String>(props);        		                        
		        kafkaConsumer.subscribe(topics, new ConsumerRebalanceListener() {
	                public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
	                    System.out.printf("%s topic-partitions are revoked from [SECOND] this consumer\n", Arrays.toString(partitions.toArray()));
	                }
	                public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
	                    System.out.printf("%s topic-partitions are assigned to [SECOND] this consumer\n", Arrays.toString(partitions.toArray()));
	                }
	            });

		        
		        consumerLoop(kafkaConsumer);
			}
		});
        */
/*        exService.execute(new Runnable() {			
			@Override
			public void run() {				
				Properties props=getConfiguration("third");
		        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String,String>(props);        		                        
		        kafkaConsumer.subscribe(topics, new ConsumerRebalanceListener() {
	                public void onPartitionsRevoked(Collection<TopicPartition> partitions) {	                	
	                	
	//                    System.out.printf("%s topic-partitions are revoked from [THIRD] this consumer\n",Arrays.toString(partitions.toArray()));
	                }
	                public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
	  //                System.out.printf("%s topic-partitions are assigned to [THIRD] this consumer\n", Arrays.toString(partitions.toArray()));
	                }
	            });
		        consumerLoop(kafkaConsumer);
			}
		});
*/        
       /* 
        exService.execute(new Runnable() {			
			@Override
			public void run() {				
		        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String,String>(props);        
		        List<String> topics = Arrays.asList(new String[]{"sample-topic"});                
		        kafkaConsumer.subscribe(topics);
		        consumerLoop(kafkaConsumer);								
			}
		});*/
        
  /*      exService.shutdown();*/
		
		Properties props=getConfiguration("third");
		final String topicName="Kirana";
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
	
	
	public void consumerLoop( KafkaConsumer<String,String> kafkaConsumer) {
					

		try {
        	while(true) {

        	   System.out.println("Going to read next message>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        	   Duration duration=Duration.ofMillis(1000);        	   
        	   ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(duration);        	   
               Set<TopicPartition> topicPartitions=kafkaConsumer.assignment();               	
               printBegningOffset(kafkaConsumer, topicPartitions);
               printEndOffset(kafkaConsumer, topicPartitions);
               printCommitedOffset(kafkaConsumer, topicPartitions);
               System.out.println("Thread"+Thread.currentThread().getName()+" "+"count=="+consumerRecords.count());
               //Thread.sleep(3000);        	           	   
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
	
	public static void main(String[] args) {
		
		new ConsumerEx().consume();
	}
	
	
   public void	printEndOffset(KafkaConsumer kafkaConsumer,Collection<TopicPartition> topicPartitions)
	{		
	   
          Map<TopicPartition, Long> endPartMap = kafkaConsumer.endOffsets(topicPartitions);                  
          endPartMap.forEach((k,v)->{
        	  System.out.println("End Offset="+k+" "+v);
       });	   
	}
	
   
   public void printBegningOffset(KafkaConsumer kafkaConsumer,Collection<TopicPartition> topicPartitions) {	   	  
       Map<TopicPartition, Long> part = kafkaConsumer.beginningOffsets(topicPartitions);       
       part.forEach((k,v)->{                	       	  
     	  System.out.println("Begining Offset="+k+" "+v);                	  	
       });                                                     
   }
   
   public void printCommitedOffset(KafkaConsumer kafkaConsumer,Collection<TopicPartition> topicPartitions) {	   	  
       
	   if(topicPartitions.iterator().hasNext()) {
		   OffsetAndMetadata metdata = kafkaConsumer.committed(topicPartitions.iterator().next());       
	       //System.out.println("last commited offset "+(metdata.offset()-1));	       
	   }
   }
	
	public Properties getConfiguration(String consumerid) {
		Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("group.id", "testgroup-0-13");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        //props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put("enable.auto.commit", "false");
        props.put("session.timeout.ms", 10000);        
        props.put("max.poll.records",3);
        props.put("consumer.id",consumerid);
        props.put("heartbeat.interval.ms",1000);        
        props.put("max.poll.interval.ms",30000);
        props.put("max.partition.fetch.bytes", "1024");

        //SSL Settings
        //props.put("security.protocol", "SSL");
        //props.put("ssl.endpoint.identification.algorithm","");
        
	   //props.put("ssl.truststore.location","D:/Tools/kafka_2.11-2.1.0/kafka.client.truststore.jks");
        
        //props.put("ssl.truststore.location","kafka.client.truststore.jks");
	    //props.put("ssl.truststore.password","cbx#123");
	   
        //props.put("auto.commit.interval.ms", 1);
        //request.timeout.ms
        // This is how to control number of records being read in each poll
		return props;  
	}
	
	public void createTrustStore() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
		//String kafkaSslCer="MIIEATCCAumgAwIBAgIUDV3VVMrldaOBjfg5e3BjigwFixcwDQYJKoZIhvcNAQELBQAwgY8xCzAJBgNVBAYTAklOMQswCQYDVQQIDAJNSDENMAsGA1UEBwwEUFVORTESMBAGA1UECgwJSW50ZWxsZWN0MQwwCgYDVQQLDANTRFUxEjAQBgNVBAMMCWxvY2FsaG9zdDEuMCwGCSqGSIb3DQEJARYfYW5raXQucGF0d2FAaW50ZWxsZWN0ZGVzaW5nLmNvbTAeFw0xOTA0MjIwNTM3MTJaFw0yMDA0MjEwNTM3MTJaMIGPMQswCQYDVQQGEwJJTjELMAkGA1UECAwCTUgxDTALBgNVBAcMBFBVTkUxEjAQBgNVBAoMCUludGVsbGVjdDEMMAoGA1UECwwDU0RVMRIwEAYDVQQDDAlsb2NhbGhvc3QxLjAsBgkqhkiG9w0BCQEWH2Fua2l0LnBhdHdhQGludGVsbGVjdGRlc2luZy5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDNW55RM4r4F2+35Ml4H02YR67jQly7V6Qz8q6DLf1SrYuW/S5/9Ne6OC/0lpMH8a48/SorFxoEsEbiZ7UvOE/MX761IQNFW9b7pIZy0PFmw8vylhmUbWonO9GmaXx7TYbhOjFfUZ8+WgxccNEEKsgNNGKaDxWEnCsPuRFWcBR9+qLU5XXNfHauMFJLrd4Q0WA/Uzuh13fRZq5M2ydEe/2z4QP7GYBN+GKriXPxtJNtZTMOtydIdYhVljwdV3lQgZgA5iGAxEHjB8ld9BnpfyUKbnzXYE5YgYbJBUY13838XfL9WH69L2bD7wVOlgeBaoQ8xqZPU1rugHViENDAjV7DAgMBAAGjUzBRMB0GA1UdDgQWBBSoNtV1gVatr0lzn9EZ47ZnvgEU0jAfBgNVHSMEGDAWgBSoNtV1gVatr0lzn9EZ47ZnvgEU0jAPBgNVHRMBAf8EBTADAQH/MA0GCSqGSIb3DQEBCwUAA4IBAQBGsThYqv6I/51thq743b8hyLsQLiPiYWsdlN56VxT9qCMFV2jaS7fdUapD5Elkhcezc3ECN4808WwEINldLFRPBv9xSB8I0KnLhLATPRQXnAqOG/SIjBF/uLQQ77VqKfzhmkueZwcTcA/zUU2LgwY8QJNjB6t8ZmmUz0ykWyFTVbRYG1Gjj8yM1tRJXyIb4GMCHO1I2U3XH5nfF+pbIZynfKH1PvFhC54FECOnRAkJqEztIrGXE11uS6x2qhI2Gncw1xHBEhaUbFRc3rp+KsX5f+7XqL28a1E/89UzQYZ0GTFq8o72eLqgKdmxlGToqjPNvS3adhOVJnREqBa2B0r8";
		String kafkaSslCer="MIIFmDCCBIACFFueCALLqvZmsthVEiq9MaE713jvMA0GCSqGSIb3DQEBCwUAMIGPMQswCQYDVQQGEwJJTjELMAkGA1UECAwCTUgxDTALBgNVBAcMBFBVTkUxEjAQBgNVBAoMCUludGVsbGVjdDEMMAoGA1UECwwDU0RVMRIwEAYDVQQDDAlsb2NhbGhvc3QxLjAsBgkqhkiG9w0BCQEWH2Fua2l0LnBhdHdhQGludGVsbGVjdGRlc2luZy5jb20wHhcNMTkwNDIzMDcyOTM3WhcNMjAwNDIyMDcyOTM3WjBhMQswCQYDVQQGEwJpbjELMAkGA1UECBMCbWgxDTALBgNVBAcTBHB1bmUxEjAQBgNVBAoTCWludGVsZWxjdDESMBAGA1UECxMJaW50ZWxsZWN0MQ4wDAYDVQQDEwVhbmtpdDCCA0IwggI1BgcqhkjOOAQBMIICKAKCAQEAj3k12bmq6b+r7Yh6z0lRtvMuxZ47rzcY6OrElh8+/TYG50NRqcQYMzm4CefCrhxTm6dHW4XQEa24tHmHdUmEaVysDo8UszYIKKIv+icRCj1iqZNFNAmg/mlsRlj4S90ggZw3CaAQV7GVrc0AIz26VIS2KR+dZI74g0SGd5ec7AS0NKasLnXpmF3iPbApL8ERjJ/6nYGB5zONt5K3MNe540lZL2gJmHIVORXqPWuLRlPGM0WPgDsypMLg8nKQJW5OP4o7CDihxFDk4YwaKaN9316hQ95LZv8EkD7VzxYj4VjUh8YI6X8hHNgdyiPLbjgHZfgi40K+SEwFdjk5YBzWZwIdALr2lqaFePff3uf6Z8l3x4XvMrIzuuWAwLzVaV0CggEAFqZcWCBIUHBOdQKjl1cEDTTaOjR4wVTU5KXALSQu4E+W5h5L0JBKvayPN+6x4J8xgtI8kEPLZC+IAEFg7fnKCbMgdqecMqYn8kc+kYebosTnRL0ggVRMtVuALDaNH6g+1InpTg+gaI4yQopceMR4xo0FJ7ccmjq7CwvhLERoljnn08502xAaZaorh/ZMaCbbPscvS1WZg0u07bAvfJDppJbTpV1TW+v8RdT2GfY/Pe27hzklwvIk4HcxKW2oh+weR0j4fvtf3rdUhDFrIjLe5VPdrwIRKw0fAtowlzIk/ieu2oudSyki2bqL457Z4QOmPFKBC8aIt+LtQxbh7xfb3gOCAQUAAoIBAHrezSr8Yohbc22UJv45RPfcyvHti7nHBVAk9t0UiHFmUVSI2QfEfmFHrPjSFIsTpEOr0dIM0GTXiD/YRXCMJK2NE5DRUwHvgJ1lLtsrPYJa1VFM7ylheHxRCS88O/1Ityyj7ywAjwQvl06hxfbSwo4E/a7ZH9lOIppdtpakaHJ1fw40TSM4Y4hzwTjBxVkX0BPC9hS5WUI6FHQY7xrRNe2kMcC5BIqA0U741mHv83ELWX07hNkfG1wNk1Rn3HyX+qUHSaenTpV89ZqKTAjS+6N4GRZuhzVRk2Yzz5hY4hEJ6o/AMiELaQlJCihbpcL5t9qxO1ftFRFrJAQgw+51PBUwDQYJKoZIhvcNAQELBQADggEBABVJL0Xc7620LRhJIf6zvfC0hPIuzu0qRTyhlRai63R8A5bRMR8FpGw+zwavW3w2td0FbbeDB0tds9dvYk/zMIHJ8e/gaS6bsCTC9xhJ9VJgZOHfT99JmdDjqcv4KnYoVF5879plKCO2WghCapA/F6WdwZEnlPrfOUHUj05ddD+xbW15rvE7uF0c1pYbDy1fBb4d1EYXSPfQEotMWLrtfNCt1YbrSFjDVNsHrMSWNTRZsMkbQknvCyZxM4CFuHhbsBWE2vLJZXP/qZRrMqdw7uFkG3BYQ2I8TiWj7Xglc+VCPlwtghdl+l/c6yAla1Liu7xCisjg2kjZie55mWDjfZk=";
		
		String keyStorePass="cbx#123";
		KeyStore ks=KeyStore.getInstance("JKS");
		ks.load(null,"changeit".toCharArray());	//To load keystore (No need to create keystore file) 	 		
		//create certificate from base64 format string
		CertificateFactory cf = CertificateFactory.getInstance("X.509");		
		InputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(kafkaSslCer));
		Certificate certificate = cf.generateCertificate(inputStream);
		ks.setCertificateEntry("cbx-root-ca", certificate); //setting client
		FileOutputStream fos=new FileOutputStream("kafka.client.truststore.jks");
		ks.store(fos,keyStorePass.toCharArray());		
	}

}
