package net.iponweb.intro.pure

import net.iponweb.intro.Wallet

final case class Charge(wallet: Wallet, amount: Int) {

  def combine(other: Charge): Charge =
    if (wallet.number == other.wallet.number) {
      Charge(wallet, amount + other.amount)
    } else {
      throw new IllegalArgumentException("Failed to combine charges for different wallets")
    }
}
