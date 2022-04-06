name := "POC-Peaceland"

version := "0.2"

scalaVersion := "2.12.10"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.7.0"
libraryDependencies += "org.apache.kafka" %% "kafka" % "2.7.0"
libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "3.2.2"
libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "3.2.2"
libraryDependencies += "org.apache.spark" %% "spark-core" % "3.1.1"

// pour la lecture de csv
libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.8"

libraryDependencies += "org.slf4j" % "slf4j-api" % "2.0.0-alpha1"
libraryDependencies += "org.slf4j" % "slf4j-simple" % "2.0.0-alpha1"

libraryDependencies += "com.github.pjfanning" % "scala-faker_2.12" % "0.5.2"

libraryDependencies += "commons-io" % "commons-io" % "2.4"


