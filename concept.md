
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
