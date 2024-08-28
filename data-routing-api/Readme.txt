This is a driver that is used to drive the data routing library from a flow xml file for demo/testing purposes
It will beuild a fat jar for convenience with name data-routing-api-0.0.17-SNAPSHOT-jar-with-dependencies.jar


Helpful commands:
mvn clean install -Dpmd.skip=true -DskipTests     
 
Start the Zookeeper server by executing the command: bin/zookeeper-server-start.sh config/zookeeper.properties
Start the Kafka server by executing: bin/kafka-server-start.sh config/server.properties

bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 10 --topic outputTopic1
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 10 --topic outputTopic2

bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic outputTopic1



java -cp data-routing-api-0.0.17-SNAPSHOT-jar-with-dependencies.jar demo.driver.OutputDemo 25000 "/tmp/data-routing/New_Mediation_Flow2.xml" "/tmp/data-routing/avro"

Params:
number of 4 message batches to send
location of flow to use
location of schema 



java -cp data-routing-api-0.0.17-SNAPSHOT-jar-with-dependencies.jar demo.driver.OutputDemo 10000 "C:\GIT\DATA_ROUTING_OF_STUFF\data-routing\data-routing-api\src\main\resources\1TopicFlows\EqualityFlow.xml" "C:\GIT\DATA_ROUTING_OF_STUFF\data-routing\data-routing-api\src\main\resources\avro"



kafka-topics --describe --zookeeper localhost:2181  --topic outputTopic1