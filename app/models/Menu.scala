package models

object Menu extends Enumeration {
  type Menu = Value
  val Home = Value("home")
  val Offer = Value("offer")
  val Producers = Value("producers")
  val Contact = Value("contact")
  val UserLogin = Value("userlogin")
  val ChangeLang = Value("changelang")
}
