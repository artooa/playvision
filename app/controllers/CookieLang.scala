package controllers

import play.api.mvc.{Action, Controller, Cookie, RequestHeader}
import play.api.i18n.Lang
import play.api.data.Form
import play.api.data._
import play.api.data.Forms._
import play.api.Logger

trait CookieLang extends Controller {

  def changeLocale(locale: String) = Action { implicit request =>
    val referrer = request.headers.get(REFERER).getOrElse(HOME_URL)
    if (locale.isEmpty()) {
      Logger.logger.debug("The locale can not be change to : " + locale)
      BadRequest(referrer)
    } else {
      Logger.logger.debug("Change user lang to : " + locale)
      Redirect(referrer).withCookies(Cookie(LANG, locale))
    }
  }

  override implicit def request2lang(implicit request: RequestHeader) = {
    request.cookies.get(LANG) match {
      case None => super.request2lang(request)
      case Some(cookie) => Lang(cookie.value)
    }
  }

  protected val LANG = "lang"
  protected val HOME_URL = "/"
}
