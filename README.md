# Scala Analysis with Kafka Stream

The goal of this project is to learn how to use Kafka.
To do that, we imagined a scenario where a drone will spy on people, collect informations about them and use a kafka stream to send these informations to a central terminal for analysis. This program will use Scala, Spark, Kafka and HDFS. Scala faker is also used to create the dataset

## -- Global architecture --
<img width="777" alt="image" src="https://user-images.githubusercontent.com/75072085/166921356-d4b024ac-e136-4b46-b7da-ff579876f8e3.png">

## -- How the project works--

The drone (producer) collects the data of people in its way, create a Kafka topic and send these data to a consumer.

The drone report regroup many informations about people like their name, age, social points and the word that the drone heard from them.

The Consumer connect to the topic created by the drone. The data collected will be transmitted to a datalake which will store the data of the drone, the alerts, so that peaceland can analyze them.

The writer receive the data from the consumer and write it in the database with the Hadoop Distributed File System (HDFS).

The Spark Analyzer will analyze all the values collected by the drone to show many measures and send the most 

The alerts (events) is triggered when a certain person have a social score lower than a certain threshold. It can alos be triggered if a "forbidden" word is heard from a person. Then the program print the name of the person, his decreased social score and the word heard.


## -- How to launch the project--
### Launching Kafka serveur
Launch Zookeeper in a terminal /

Launch Kafka server in another terminal (window or tab)/


### Scala analysis
Launch SBT /

Select the producer program  /

Open a second terminal /

Launch SBT again and select the consumer program this time/
