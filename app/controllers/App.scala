package controllers

import models._
import play.api._
import play.api.Play.current
import play.api.mvc._
import views._
import play.api.Logger
import play.api.i18n.Lang
import play.api.libs.ws._
import play.api.Configuration._
import play.api.libs.concurrent.Execution.Implicits._

object App extends Controller with CookieLang {

  override val HOME_URL = routes.App.home().url
  Logger.info("HOME_URL: " + HOME_URL)

  def render(p: String) = Action {

    val content = Play.current.getExistingFile("docs/" + p).toString()
    Ok(content)
  }

// 	public static void frontend() {
//		renderArgs.data.remove("out");
//		Page page = Page.findPageForRequest(request.domain, request.path);
//
//		if (page != null && page.parts.size() >= 1) {
//			Template templatePlay = TemplateLoader.loadString(page.getInheritedLayout().content);
//			Map args = new HashMap();
//			args.put("page", page);
//			//renderTemplate(templatePlay.name, page);
//			renderHtml(templatePlay.render(args));
//		} else {
//			notFound();
//		}
//	}

  def home = Action { implicit request =>
    val browserLang = request.acceptLanguages.map(_.code).mkString(", ")
    val ipAddress = request.headers.get("X-Forwarded-For") match {
      case Some(ip: String) => ip
      case None => request.remoteAddress
    }
    Logger.info(
      ipAddress + "; " + browserLang + "; " + "Header: " + request.headers.get(
        USER_AGENT))
    Ok(views.html.home(Menu.Home.toString))
  }

  def offer = Action { implicit request =>
    Ok(views.html.offer(Menu.Offer.toString))
  }

  def producers = Action { implicit request =>
    Ok(views.html.prod(Menu.Producers.toString))
  }

  def lang = Action { request =>
    Ok(views.html.home(Menu.Home.toString))
  }

  /*


 def myAction = Secured("admin", "12345secret") {
    Action { implicit request =>


      Ok("Erfolg!!")
  }
 }
   //basic authentication

  def Secured[A](username: String, password: String)(action: Action[A]) = Action(action.parser) { request =>
  request.headers.get("Authorization").flatMap { authorization =>
    authorization.split(" ").drop(1).headOption.filter { encoded =>
      new String(org.apache.commons.codec.binary.Base64.decodeBase64(encoded.getBytes)).split(":").toList match {
        case u :: p :: Nil if u == username && password == p => true
        case _ => false
      }
    }.map(_ => action(request))
    }.getOrElse {
      Unauthorized.withHeaders("WWW-Authenticate" -> """Basic realm="Secured"""")
    }
  }
	*/

}
