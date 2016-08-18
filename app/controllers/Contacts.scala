package controllers

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import views._
import models.{Contact, ContactInformation}
import play.api.Logger
import play.api.mvc.{Flash, Action, Controller}
import play.api.i18n.Messages
import play.api.Play.current
import com.typesafe.plugin.MailerPlugin
import java.text.SimpleDateFormat
import com.typesafe.plugin._
import play.api.i18n.Lang

object Contacts extends Controller with CookieLang {

  val mail = use[MailerPlugin].email

  /**
    * Contact Form definition.
    */
  val contactForm: Form[Contact] = Form(
    // Defines a mapping that will handle Contact values
    mapping(
      "firstname" -> nonEmptyText,
      "lastname" -> nonEmptyText,
      "email" -> optional(email),
      "phone" -> nonEmptyText,
      "company" -> optional(text),
      "address" -> nonEmptyText,
      "postalcode" -> nonEmptyText,
      "city" -> nonEmptyText,
      "country" -> optional(ignored("Schweiz")),
      // Defines a repeated mapping
      "info" ->
        mapping(
          "moreInfo" -> boolean,
          "membership" -> boolean,
          "subscription_small" -> boolean,
          "subscription_large" -> boolean,
          "extraProducts" -> boolean,
          "maillinglist" -> boolean,
          "callme" -> boolean,
          "join" -> boolean,
          "remark" -> optional(text)
        )(ContactInformation.apply)(ContactInformation.unapply)
    )(Contact.apply)(Contact.unapply)
  )

  /**
    * Display an empty form.
    */
  def form = Action { implicit request =>
    val cForm =
      if (request.flash.get("error").isDefined)
        this.contactForm.bind(request.flash.data)
      else
        this.contactForm
    Ok(html.contact.form(cForm));
  }

  /**
    * Handle form submission.
    */
  def submit = Action { implicit request =>
    val cForm = this.contactForm.bindFromRequest()

    cForm.fold(
      hasErrors = { form =>
        Redirect(routes.Contacts.form()).flashing(
          Flash(form.data) + ("error" -> Messages("validation.errors")))
      },
      success = { contact =>
        //Contact.add(contact)
        save(contact, request2lang(request))
        val message = Messages("contacts.new.success", contact.firstname)
        Logger.info(message)
        Ok(html.contact.summary(contact)).flashing("success" -> message)

      }
    )
  }

  def save(c: Contact, lang: Lang): Unit = {
    val name = c.firstname + " " + c.lastname + " " + new SimpleDateFormat(
        "dd.MM.yyyy HH:mm").format(new java.util.Date())
    val mailBody = c.printContact + "\n" + c.info.printRequest(lang)

    Logger.info("Form Submit: " + name + "\n" + c + " \n " + mailBody)
    mail(c, name, mailBody)
  }

  def mail(c: Contact, subj: String, mailBody: String): Unit = {
    mail.setSubject("Contact - " + subj)
    mail.setFrom("PLAYVISION <info@artooa.ch>")
    mail.setRecipient("PLAYVISION <info@artooa.ch>")
    if (c.email.isDefined) mail.setCc(c.email.get)

    //sends html
    //mail.sendHtml("<html>html</html>" )
    //sends text/text
    mail.send(mailBody)
    //sends both text and html
    //mail.send( "text", "<html>html</html>")

  }
}
