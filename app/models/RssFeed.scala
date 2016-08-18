package models

import play.i18n.Messages
import play.api.Play.current
import play.api.i18n.Lang
import scala.xml._

/* TODO: refactor
 *
 */

case class RssFeed(val url: String) {

  def downloadItems(): List[NewsItem] = {

    // load the xml object into memory
    val root = XML.load(url)

    // parse feed into Item data structure
    (root \\ "item").map(buildItem(_)).toList
  }

  def buildItem(node: Node): NewsItem = {

    val item = new NewsItem(this,
                            (node \\ "title").text,
                            (node \\ "description").text,
                            (node \\ "link").text,
                            (node \\ "guid").text,
                            (node \\ "pubDate").text,
                            (node \\ "category").map(_.text).toList)

    println("ITEM: " + item)
    item
  }

  def getCategories(node: Node): List[String] = {

    //for (c <- node) yield c.text
    node.map(_.text).toList
  }
}

case class NewsItem(val parent: RssFeed,
                    val title: String,
                    val description: String,
                    val link: String,
                    val guid: String,
                    val pubDate: String,
                    val category: List[String]) {

  override def toString: String =
    title + " " + guid + " " + category + " " + isNews("de")

  def isNews(locale: String): Boolean = {
    locale match {
      case "fr" => category.contains("nouvelle")
      case _ => category.contains("news")
    }
  }

}
