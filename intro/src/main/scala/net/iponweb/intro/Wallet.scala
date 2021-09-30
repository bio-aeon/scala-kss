package net.iponweb.intro

final case class Wallet(number: String, userId: Int, details: String) {

  // Make http call and save result to DB
  def charge(price: Int) = ???
}
