package views
import models._
import controllers.Includes

object Test extends App {

  println(Menu.Home)
}

object Menux {

  object WeekDay extends Enumeration {
    type WeekDay = Value
    val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
  }
  import WeekDay._

  def isWorkingDay(d: WeekDay) = !(d == Sat || d == Sun)

  WeekDay.values filter isWorkingDay foreach println

  Includes.at("", "")
}
