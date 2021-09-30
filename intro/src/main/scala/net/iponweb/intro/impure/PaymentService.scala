package net.iponweb.intro.impure

import net.iponweb.intro.Wallet

trait PaymentService {
  // Make http call and save result to DB
  def charge(wallet: Wallet, price: Int): Unit
}
