package net.iponweb.intro.pure

trait PaymentService {
  // Make http call and save result to DB
  def performCharge(charge: Charge): Unit

  protected def groupByWallet(charges: List[Charge]): List[Charge] =
    charges.groupBy(_.wallet.number).values.map(_.reduce(_ combine _)).toList
}
