import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import com.github.tototoshi.csv._

import java.util
import java.util.Properties
import java.time.Duration
import java.io.File

import scala.collection.JavaConverters._

object ReportConsumerTest extends App {

  val props:Properties = new Properties()
  props.put("bootstrap.servers","localhost:9092")
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("group.id", "mygroup")

  val consumer = new KafkaConsumer[String, String](props)
  val topic = "Report_Drone"
  consumer.subscribe(util.Collections.singletonList("Report_Drone"))

  def ecrire_alerte(citoyen : Report_Drone_print.Citoyen, report_str : String)  {

    // Si le score du citoyen est trop bas, l'alerte s'enclenche
    if (citoyen.score < 10) {
      val alert_string = "Le personnage " + Report_Drone_print.afficher_citoyen(citoyen) + " qui est très grave !"
      println(alert_string)
// Ecrire dans le fichier alerte les personnes avec un score bas
      val ecrire_alerte = CSVWriter.open("data/alert.csv", append = true)
      val lignes_dataset = report_str.split(";")
      val list_to_alerte = List(lignes_dataset(0), lignes_dataset(1), lignes_dataset(2), lignes_dataset(5), citoyen.name, citoyen.score.toString)
      ecrire_alerte.writeRow(list_to_alerte)
      ecrire_alerte.close()
  

    }

  }
// Ecrire dans le fichier report les personnes analysées
  def ecriture_de_report(report : Report_Drone_print.Report) = {
    val ecriture_report = CSVWriter.open("data/report.csv", append = true)
    val texte_a_ajouter = report.toString
    val liste_a_ajouter = texte_a_ajouter.split(";").map(_.trim).toList
   ecriture_report.writeRow(liste_a_ajouter)
   ecriture_report.close()
  }
// Ecoute des reports envoyés par le Producer
  def ecoute_records(records : ConsumerRecords[String, String]) = {
    try {
      val report_from_record = records.asScala.head.value()
      val report = Report_Drone_print.report_from_string(report_from_record)
      Report_Drone_print.print_report(report)
      report.citoyen_in_area.foreach(ecrire_alerte(_, report_from_record))
      ecriture_de_report(report)
    }
    catch {
      case e:Exception => println("Pas de report")
    }
  }

  def ecoute() {
    val records = consumer.poll(5000)
    ecoute_records(records)
    Thread.sleep(5000)
    ecoute()
  }


  ecoute()

}
