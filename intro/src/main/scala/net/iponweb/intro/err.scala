package net.iponweb.intro

object err {
  def div(x: Int, y: Int): Int =
    if (y == 0) {
      throw new IllegalArgumentException("Impossible to divide by 0")
    } else {
      x / y
    }
}
