
#### session.timeout.ms
The timeout used to detect consumer failures when usingKafka's group management facility. The consumer sends periodic heartbeats
to indicate its livenessto the broker. If no heartbeats are received by the broker before the expiration of this session timeout,
then the broker will remove this consumer from the group and initiate a rebalance. Note that the valuemust be in the allowable 
range as configured in the broker configuration by <code>group.min.session.timeout.ms</code>and 
<code>group.max.session.timeout.ms</code>.

#### heartbeat.interval.ms
it is used to have other healthy consumers aware of the rebalance much faster. If coordinator triggers a rebalance, other consumers
will only know of this by receiving the heartbeat response with REBALANCE_IN_PROGRESS exception encapsulated. 
Quicker the heartbeat request is sent, faster the consumer knows it needs to rejoin the group

#### max.poll.interval.ms
It is for user thread. If message processing logic is too heavy to cost larger than this time interval,
coordinator explicitly have the consumer leave the group and also triggers a new round of rebalance.

#### auto.offset.reset

earliest: automatically reset the offset to the earliest offset

latest: automatically reset the offset to the latest offset

none: throw exception to the consumer if no previous offset is found for the consumer's group

* You have a consumer in a consumer group group1 that has consumed 5 messages and died. Next time you start this consumer it won't even    use that auto.offset.reset config and will continue from the place it died because it will just fetch the stored offset from the offset storage.

* You have messages in a topic (like you described) and you start a consumer in a new consumer group group2. There is no offset stored anywhere and this time the auto.offset.reset config will decide whether to start from the beginning of the topic (earliest) or from the end of the topic (latest)

* One more thing that affects what offset value will correspond to smallest and largest configs is log retention policy. Imagine you have a topic with retention configured to 1 hour. You produce 5 messages, and then an hour later you post 5 more messages. The largest offset will still remain the same as in previous example but the smallest one won't be able to be 0 because Kafka will already remove these messages and thus the smallest available offset will be 5


#### Default Values
group.max.session.timeout.ms  30s
group.min.session.timeout.ms  6s
