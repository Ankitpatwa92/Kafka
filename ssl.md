ssl configuration for kafka

#### Add following in kafka server.properties

```
listeners=PLAINTEXT://:9092,SSL://localhost:9091
advertised.listeners=PLAINTEXT://localhost:9092,SSL://localhost:9091
ssl.keystore.location = /kafka.server.keystore.jks
ssl.keystore.password = changeit
ssl.key.password = changeit
ssl.truststore.location = /kafka.server.truststore.jks
ssl.truststore.password = changeit
ssl.client.auth = none
```
