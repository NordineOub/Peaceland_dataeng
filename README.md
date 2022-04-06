# POC Peaceland

## Context 
The goal is to write a poc demonstrating a working architecture of peaceland.

The typical architecture will have 4 components (probably 4 Main) :
1) a program simulating the drone and sending drone-like data to your solution (see subject for details on a message). This program should not be distributed (no spark).
Your system will store message in a distributed stream making it available to the component 2 and 3. (this part should not be done with spark)
2) handle riot alert message from stream 
3) store message formatted as drone message in a distributed storage (ex: HDFS/S3)
4) analyse stored data with a distributed processing component (like spark). As a proof of your system capacity to analyse the store data answer 4 questions of your choice. (ex: is there more riot during the week or during week-end?). 
