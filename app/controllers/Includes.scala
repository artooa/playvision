package controllers

import java.io.File
import scala.io.Source
import play.api.Play
import play.api.Play.current
import play.api.templates.Html
import play.api.templates.Html.apply
import scala.io.Codec.apply
import play.Logger
import play.mvc.Controller
import play.api.mvc.Action

/**
  * Singleton that serves html files  from an external folder.
  *
  * You can use this controller in any application, just by declaring the appropriate route. For example:
  * {{{
  * GET     /assets/\uFEFF*file               controllers.ExternalAssets.at(path="/home/peter/myplayapp/external", file)
  * GET     /assets/\uFEFF*file               controllers.ExternalAssets.at(path="C:\external", file)
  * GET     /assets/\uFEFF*file               controllers.ExternalAssets.at(path="relativeToYourApp", file)
  * }}}
  *
  */
object Includes extends Controller with CookieLang {

  val AbsolutePath = """^(/|[a-zA-Z]:\\).*""".r
  val CmsHome = "test-content" //TODO CONFIGURE THIS

  def get(path: String, file: String) = Action {

    val content = at(path, file)
    Ok(content)
  }

  /**
    * Generates an `Action` that serves a static resource from an external folder
    *
    * @param absoluteRootPath the root folder for searching the static resource files such as `"/home/peter/public"`, `C:\external` or `relativeToYourApp`
    * @param file the file part extracted from the URL
    */
  def at(path: String, file: String): Html = {
    val rootPath = CmsHome + path
    Logger.info("rootPath: " + rootPath + " file: " + file) //TODO remove logger

    val fileToServe = rootPath match {
      case AbsolutePath(_) => new File(rootPath, file)
      case _ => new File(Play.application.getFile(rootPath), file)
    }

    Html(
      if (fileToServe.exists) {
        val r =
          Source.fromFile(fileToServe, 64)(scala.io.Codec("UTF-8")).mkString
        parseImg(path, r)
      } else {
        "<h2>ERROR NotFound: " + file + "</h2>"
      }
    )
  }

  def parseImg(path: String, input: String): String = {
    import scala.util.matching.Regex.Match
    val imgPattern = """<img[^>]+ src="([^">]+)"""".r

    //val p = """<img(\s+.*?)(?:src\s*=\s*(?:'|\")(.*?)(?:'|\"))(.*?)/>""".r
    val p = """(?:src\s*=\s*((?:'|\")([^http.].*?)(?:'|\")))""".r

    val link = p replaceAllIn (input, (m: Match) => {
      println("group 0: " + m.group(0))
      println("group count: " + m.groupCount)
      println("group 1: " + m.group(1))
      val n = m.group(1)
      val u = path + "/" + (n.filterNot(_ == '"'))
      """src="/cms/""" + u + """/")""""
    })
    link
  }

  //TODO <img alt="logo" src="@routes.Assets.at("/docs", "img/playvision-logo.png")">
  //http://localhost:9000/img/playvision-logo.png
  //von <img src="img/playvision-logo.png" alt="LOGO" />
  //zu <img src="img/playvision-logo.png" alt="LOGO" />
  //rootPath: docs/home file: aktuell.html
}
