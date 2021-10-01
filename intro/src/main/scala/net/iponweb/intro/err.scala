package net.iponweb.intro

object err {
  def div(x: Int, y: Int): Option[Int] =
    if (y == 0) {
      None
    } else {
      Some(x / y)
    }
}
