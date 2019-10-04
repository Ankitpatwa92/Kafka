# Kafka
Kafka Important and Basic Commands

#### Kafka Default Port 9092 , Zookeeper Default Port 2181

#### Start Zookeeper
```bin/zkServer.sh   config\zookeeper.properties```

#### Start kafka in background
 ```./bin/kafka-server-start.sh  -daemon  config/server.properties    ///Daemon is used to start kafka in background```    
 
#### Create kafka topic 
 ```bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test```

#### See all Topic 
```./bin/kafka-topics.sh --list --zookeeper localhost:2181 .```

#### Delete Topic 
```./bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic demo .```

#### Start Producer
```bin/kafka-console-producer.sh --broker-list localhost:9092 --topic topic-name```

#### Start Consumer 
```bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 —topic topic-name  --from-beginning```

#### Alter Topic 
```
bin/kafka-topics.sh —zookeeper localhost:2181 --alter --topic topic_name  --parti-tions count
//Using above command partition count can be changed
```
#### Describe Topic
```
bin/kafka-topics.sh --describe --topic test-topic --zookeeper localhost:2181
```
#### Get offset detail of particluar consumer group
```
bin/kafka-consumer-groups.sh  --bootstrap-server localhost:9092 --describe --group testgroup
```

#### Where is Kafka Log Location
```
Go to config/server.properties   There you will find 
log.dirs=/path/to/your/log/directory
```

#### Read message from kafka consume 
```
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic my-topic --from-beginning
```


#### Send message to topic 
```
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic ACCOUNT-BALANCES-INBOUND-TOPIC
```

### Describe Consumer Grop
```

D:\Tools\kafka_2.11-2.1.0>bin\windows\kafka-run-class.bat kafka.admin.ConsumerGroupCommand --bootstrap-server localhost:9092 --group g1 --describe

TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID                                     HOST            CLIENT-ID
dryfruit        0          90              90              0               consumer-1-eac23e20-93af-4215-a883-8f7e493468bc /127.0.0.1      consumer-1
dryfruit        1          90              90              0               consumer-1-eac23e20-93af-4215-a883-8f7e493468bc /127.0.0.1      consumer-1
dryfruit        2          0               0               0               consumer-1-eac23e20-93af-4215-a883-8f7e493468bc /127.0.0.1      consumer-1
```


### Get Partition Replica and ISR detail
```
bin\windows\kafka-topics.bat --describe --zookeeper localhost:2181 --topic dryfruit

Topic:dryfruit  PartitionCount:3        ReplicationFactor:3     Configs:
        Topic: dryfruit Partition: 0    Leader: 2       Replicas: 2,0,1 Isr: 0,2
        Topic: dryfruit Partition: 1    Leader: 0       Replicas: 0,1,2 Isr: 0,2
        Topic: dryfruit Partition: 2    Leader: 2       Replicas: 1,2,0 Isr: 0,2
```
