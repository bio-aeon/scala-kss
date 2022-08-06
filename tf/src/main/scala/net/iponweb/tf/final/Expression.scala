package net.iponweb.tf.`final`

trait Expression[F[_], A] {
  def intLiteral(n: Int): F[A]

  def negate(a: F[A]): F[A]

  def add(a: F[A], b: F[A]): F[A]
}

trait Logical[F[_], A] {
  def boolLiteral(b: Boolean): F[A]

  def or(a: F[A], b: F[A]): F[A]
}

trait Division[F[_], A] {
  def divide(a: F[A], b: F[A]): F[A]
}
