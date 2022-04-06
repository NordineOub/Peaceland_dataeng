import com.github.tototoshi.csv.CSVReader
import org.apache.spark.{SparkConf, SparkContext}
import java.io.File

object Spark_Analyse extends App {

  // Initialisation Spark
  val conf = new SparkConf().setAppName("test").setMaster("local").set("spark.executor.memory", "2g")
  val sc = new SparkContext(conf)
// Import alerte et report csv
  val reportingcsv = CSVReader.open(new File("data/report.csv")).all()
  val alertingcsv = CSVReader.open(new File("data/alert.csv")).all()

  // Passage en rdd des deux datasets
  val rdd_reports = sc.parallelize(reportingcsv)
  val rdd_alerts = sc.parallelize(alertingcsv)

  // vérification des jours ouvrés
  def jours_ouvres(liste: List[String]): Int =  liste(3).take(2) match{
    case  "Mo"=> 0
    case "Tu" => 0
    case "We"=> 0
    case "Th" => 0
    case "Fr" => 0
    case _ => 1
  }
// Récupération des scores du fichier alert
  def score_alerte(liste: List[String]): Int =  liste(5).toInt
// Vérification des habitants à Peaceland City
  def peacelandcity(liste: List[String]): Int = {
    val latitude_ville = (48.8566 - liste(1).toDouble).abs
    val longitude_ville = (2.3522 - liste(2).toDouble).abs
    if (latitude_ville + longitude_ville > 30)
      0
    else
      1
  }
// Vérification des habitants à Superpeaceful Stan
  def superpeaceful(liste: List[String]): Int = {
    val latitude_ville = (103 - liste(1).toDouble).abs
    val longitude_ville = (10 - liste(2).toDouble).abs
    if (latitude_ville + longitude_ville > 30)
      0
    else
      1
  }

  def moyenne_report(liste: List[String]): Int = liste(3).split(",").length

  def print_final(): Unit = {

    // RDD size

    val size_alerts =  rdd_alerts.count() 
    val explained_size_alert = "\n Il y a \n" + rdd_alerts.count() + "\n alertes\n"
    val size_reports = rdd_reports.count()
    val explained_size_report  = "\n Il y a \n" + size_reports + "\n reports \n"


    // Week or weekend
    val n_week = rdd_alerts.map(x => jours_ouvres(x)).reduce((x, y) => x + y)
    val n_week_str = {
      "\nSur les " + size_alerts.toString + " alertes : \n " + n_week.toString + " se sont passés durant le week end.\n" + (size_alerts - n_week).toString + " en jours ouvrés.\n \n"
    }

    // Mean alerts
    val mean = rdd_alerts.map(x => score_alerte(x)).reduce((x, y) => x + y)
    val score_alerte_str = "Le score de paix moyen des alertes est de " + (mean / size_alerts.toFloat).toString + "."

    // Citoyens à Peaceland city et superpeacefulstan + restes
    val vers_P = rdd_reports.map(x => peacelandcity(x)).reduce((x, y) => x + y)
    val vers_P_str = {
      "\nSur les " + size_reports.toString + " rapports : \n" + vers_P.toString + " vivent à Peaceland City." 
    }
     val vers_S = rdd_reports.map(x => superpeaceful(x)).reduce((x, y) => x + y)
    val vers_S_str = {"\n" + vers_P.toString + " vivent à SuperPeaceful Stan."
    }
    val nulpart_str =  "\n" + (size_reports - (vers_S + vers_P)) +" ne vivent dans aucune ville.\n"

    val final_print = {
      "\n----------\nAnalyse des données sauvegardés\n\n" +  explained_size_alert + explained_size_report +n_week_str + score_alerte_str + vers_P_str + vers_S_str + nulpart_str+ "\n" 
    }
    println(final_print)
  }

  print_final()
}