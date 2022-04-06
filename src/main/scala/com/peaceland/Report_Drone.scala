import scala.util.Random
import faker._

object Report_Drone extends App {

  def report_du_drone(nb_report: Int): Unit = {
    if (nb_report > 0) {
      val test = Report_Drone_print.report_aleatoire()
      Report_Drone_print.print_report(test)
      report_du_drone(nb_report - 1)
    }
  }
  if (args.length == 0) {
    report_du_drone(1)
  }
  else {
    report_du_drone(args(0).toInt)
  }
}


object Report_Drone_print {

  case class Citoyen(name_ : String, score_ : Int) {
  
    val name = name_
    val score = score_

    override def toString() : String = {
      val str_to_return = "" + name + ":" + score
      str_to_return
    }
  }
  def Split_Score(s : String) : Citoyen = {
    val array_temp = s.split(":")
    Citoyen(array_temp(0), array_temp(1).toInt)
  }

  def print_Citoyen(Citoyen : Citoyen) = {
    val string_to_print =  Citoyen.name + " a un score de " + Citoyen.score.toString + "."
    println(string_to_print)
  }

  def afficher_citoyen(Citoyen : Citoyen): String = {
    val string_to_return = Citoyen.name + " a un score de " + Citoyen.score.toString
    string_to_return
  }
  def mauvaiscitoyen() = {
    
    val name_ = Name.name
    val number_random = new scala.util.Random
    val score_ = 0 + number_random.nextInt(30 - 0)
    
    Citoyen(name_, score_)

  }

  def print_Citoyen_with_tab(Citoyen : Citoyen) = {
    val string_to_print = "\t" + Citoyen.name + " avec un score de " + Citoyen.score.toString + "."
   println(string_to_print) 
  }

  def Citoyen_Aleatoire() = {
    val name_ = Name.name

    val number_random = new scala.util.Random
    val score_ = 0 + number_random.nextInt(100 - 0 )
    Citoyen(name_, score_)
  }



  case class Report(id_ : Int, latitude_ : String, longitude_ : String, Citoyen_in_area_ : Array[Citoyen], mots_entendus_ : List[String], date_ : String) {
  
    val id = id_
    val latitude = latitude_
    val longitude = longitude_
    val Citoyen_in_area = Citoyen_in_area_
    val mots_entendus = mots_entendus_
    val jour = date_.split(":")
    val date = jour(0) match {
      case "Mo"  => "Lundi " + jour(1)
      case "Tu"  => "Mardi " + jour(1)
      case "We"  => "Mercredi " + jour(1)
      case "Th"  => "Jeudi " + jour(1)
      case "Fr"  => "Vendredi " + jour(1)
      case _  => "Jour invalide"  // the default, catch-all
  }

    override def toString() : String = {

      val Citoyen_string = Citoyen_in_area.mkString(",")
      val words_string = mots_entendus.mkString(",")
      val string_to_return = "" + id + ";" + latitude + ";" + longitude + ";" + Citoyen_string + ";" + words_string + ";" + date
      string_to_return

    }

  }
  def mots_interdits (s : String) : String =  s match {
      case "illum"  => "\tle mot illum a été prononcé ! *emoji choqué* \n"
      case "est"  => "\tle mot est a été prononcé ! *emoji choqué*\n"
      case "suscipit"  => "\tle mot suscipit a été prononcé ! *emoji choqué*\n"
      case "asperiores"  => "\tle mot asperiores a été prononcé ! *emoji choqué*\n"
      case "broussolle"  => "\tle mot broussolle a été prononcé ! *emoji choqué*\n"
      case x  => "\t" + x + " n'est pas un mot interdit\n"  // the default, catch-all
}

  def report_from_string (s : String): Report = {

    val lignes_dataset = s.split(";")

    val id_ = lignes_dataset(0).toInt
    val latitude_ = lignes_dataset(1)
    val longitude_ = lignes_dataset(2)

    val all_Citoyen_string = lignes_dataset(3)
    val Citoyen_array = all_Citoyen_string.split(",").map(Split_Score)

    val words_array = lignes_dataset(4).split(",")

    val str_day = lignes_dataset(5)

    Report(id_, latitude_, longitude_, Citoyen_array, words_array.toList, str_day)

  }

  def print_word(str : String) = {

    val str_to_print = "\t" + str
    println(str_to_print)

  }

  def print_report(report : Report) = {

    println("\n\n--------------------\nReport du Drone\n--------------------")
    val info_drone = "Date de report :  " + report.date + "\nID du drone : " + report.id.toString + " \nPosition : " + report.latitude + "/" + report.longitude + "."
    println(info_drone)

    println("\nVoici les citoyens dans la région")

    report.Citoyen_in_area.foreach { print_Citoyen_with_tab }

    println("\nLe drone a entendu les mots suivants :")

    report.mots_entendus.foreach { print_word }
    println("\n")
    println(report.mots_entendus.map(mots_interdits))
println("\n")
  }


  def report_aleatoire() = {
  
    val id_ = scala.util.Random.nextInt(12345)
    val latitude_ = Address.latitude
    val longitude_ = Address.longitude
    val nb_Citoyen = 2 + scala.util.Random.nextInt(10)
    val Citoyen_in_area_ = List.fill(nb_Citoyen)(Citoyen_Aleatoire()).toArray
    val nb_word = 2 + scala.util.Random.nextInt(10)
    val mots_entenduss_ = Lorem.words(nb_word)

    val random = new scala.util.Random
    val array_of_day = Array("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")
    val random_day_of_the_week = random.nextInt(7)
    val day_of_the_week = array_of_day(random_day_of_the_week)
    val random_hour = random.nextInt(24)
    val hour = random_hour.toString
    val str_day = day_of_the_week + ":" + hour
    
    Report(id_, latitude_, longitude_, Citoyen_in_area_, mots_entenduss_, str_day)

  }



}
