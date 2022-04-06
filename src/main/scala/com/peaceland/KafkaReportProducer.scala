import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.common.serialization.StringDeserializer

import java.util.Properties

object ReportProducerTest extends App {

    val props:Properties = new Properties()
    props.put("bootstrap.servers","localhost:9092")
    props.put("key.serializer",
              "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer",
              "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    val topic = "Report_Drone"

    def report_du_drone(kafkaproducer : KafkaProducer[String, String]) {
    
      val report = Report_Drone_print.report_aleatoire()
      val record = new ProducerRecord[String, String](topic, report.id.toString, report.toString)
      producer.send(record)
      Thread.sleep(5000)
      report_du_drone(kafkaproducer)
    }
    try {
      report_du_drone(producer)
    }
    catch {
      
      case e:Exception => e.printStackTrace()
    
    }
    finally {
      
      producer.close()

    }

}
