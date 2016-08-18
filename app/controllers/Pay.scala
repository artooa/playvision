package controllers

//DODO
object Pay {

  def method[String] = "method"
  def username[String] = "username"
  def password[String] = "password"
  def signature[String] = "signature"
  def accountemail[String] = "email"
  val account_id = "id"
  val ok = "ok"
  val cancel = "cancel"
  val amount = "amount"

  //paypal
  def params = Map(
    // Add login infos
    ("METHOD", method),
    ("VERSION", "62.0"),
    ("USER", username),
    ("PWD", password),
    ("SIGNATURE", signature),
    //
    ("EMAIL", accountemail),
    ("RETURNURL", ok),
    ("CANCELURL", cancel),
    ("LOCALECODE", "US"),
    ("NOSHIPPING", "1"), // No shipping address
    ("SOLUTIONTYPE", "Sole"), // No paypal account needed
    ("LANDINGPAGE", "Billing"), // Non login page
    ("CHANNELTYPE", "Merchant"),
    ("PAYMENTREQUEST_0_AMT", amount),
    ("PAYMENTREQUEST_0_CURRENCYCODE", "EUR"),
    ("PAYMENTREQUEST_0_CUSTOM", "For account " + account_id),
    ("PAYMENTREQUEST_0_PAYMENTACTION", "Sale"),
    ("GIFTMESSAGEENABLE", "0"),
    ("GIFTRECEIPTENABLE", "0"),
    ("GIFTWRAPENABLE", "0"),
    ("L_PAYMENTREQUEST_0_NAME0", "Credit your prepaid account"),
    ("L_PAYMENTREQUEST_0_AMT0", amount),
    ("L_PAYMENTREQUEST_0_QTY0", "1")
  )
  // Call paypal
// val back = WS.url(api).params(params).post().getString();

  // Parse result
//	Map<String,String[]> r = UrlEncodedParser.parse(back);
//	Map<String,String> result = new HashMap<String,String>();
//	
//	for(String k : r.keySet()) {
//	    result.put(k, r.get(k)[0]);
//	}
//	
//	if(!("Success".equals(result.get("ACK")))) {
//	    Logger.error("SetExpressCheckout has failed: %s", result);
//	}
//	
//	String token = result.get("TOKEN");
//	
}
