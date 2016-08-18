object Tester extends App {
  
  
  
  object Menu extends Enumeration {
    type Menu = Value
    val Home = Value("Home")
    val Offer = Value("Offer")
    val Producers = Value("Producers")
}

  //println (Menu.Home)
  
  
  
    //TODO <img alt="logo" src="@routes.Assets.at("/docs", "img/playvision-logo.png")">
  //http://localhost:9000/img/playvision-logo.png
  
  val input = """ da daa von <img src="img/playvision-logo.png" alt="LOGO" /> und so Weiter """
  val output = """ da daa von <img src="@routes.Assets.at("/docs", "home/img/playvision-logo.png")" alt="LOGO" /> und so Weiter """  
  //zu <img src="img/playvision-logo.png" alt="LOGO" />
  
  val path = "home" // file: aktuell.html

  def parseImg(path: String, input: String): String = {
    val imgPattern = """<img[^>]+src="([^">]+)"""".r
    for(matchString <- imgPattern.findAllIn(input)){
      println(matchString.toArray)
    }
    ""
  }
  
 
  parseImg(path, input)
  //assert( == output)
 
}