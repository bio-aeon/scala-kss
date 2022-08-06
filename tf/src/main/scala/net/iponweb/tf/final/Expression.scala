package net.iponweb.tf.`final`

trait Expression[A] {
  def intLiteral(n: Int): A

  def negate(a: A): A

  def add(a: A, b: A): A
}

trait Logical[A] {
  def boolLiteral(b: Boolean): A

  def or(a: A, b: A): A
}

trait Division[A] {
  def divide(a: A, b: A): Option[A]
}
