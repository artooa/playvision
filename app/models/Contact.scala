package models
import play.i18n.Messages
import play.api.Play.current
import play.api.i18n.Lang

case class Contact(
    firstname: String,
    lastname: String,
    email: Option[String],
    phone: String,
    company: Option[String],
    address: String,
    postalcode: String,
    city: String,
    country: Option[String],
    info: ContactInformation
) {

  def printContact: String = {
    val CR = "\n"
    val sb = new StringBuffer
    sb.append(firstname).append(" ").append(lastname).append(CR)
    if (company.isDefined) sb.append(company.get).append(CR)
    sb.append(email.getOrElse(""))
      .append(CR)
      .append(phone)
      .append(CR)
      .append(address)
      .append(CR)
      .append(postalcode + " " + city)
      .append(CR)
      .append(country.getOrElse(""))
      .append(CR)
      .toString()
  }
}

case class ContactInformation(
    moreInfo: Boolean,
    membership: Boolean,
    subscription_small: Boolean,
    subscription_large: Boolean,
    extraProducts: Boolean,
    maillinglist: Boolean,
    callme: Boolean,
    join: Boolean,
    remark: Option[String]
) {

  def printRequest(implicit lang: Lang): String = {
    import play.api.i18n.Messages
    val CR = "\n"
    val star = "* "
    var sb = new StringBuffer
    sb.append(Messages("form.intro")).append(CR)
    if (moreInfo == true)
      sb.append(star).append(Messages("form.info.more")).append(CR)
    if (membership)
      sb.append(star).append(Messages("form.info.membership")).append(CR)
    if (subscription_small)
      sb.append(star)
        .append(Messages("form.info.subscription_small"))
        .append(CR)
    if (subscription_large)
      sb.append(star)
        .append(Messages("form.info.subscription_large"))
        .append(CR)
    if (extraProducts)
      sb.append(star).append(Messages("form.info.extraProducts")).append(CR)
    if (maillinglist)
      sb.append(star).append(Messages("form.info.maillinglist")).append(CR)
    if (join) sb.append(star).append(Messages("form.info.join")).append(CR)
    if (callme) sb.append(star).append(Messages("form.info.callme")).append(CR)
    if (remark.isDefined) sb.append(CR).append(remark.get).append(CR)
    sb.toString()
  }
}
