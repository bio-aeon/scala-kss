package net.iponweb.tf.initial.tagless

sealed trait Expression[A] extends Product with Serializable

object Expression {

  final case class IntLiteral(n: Int) extends Expression[Int]

  final case class Negate(a: Expression[Int]) extends Expression[Int]

  final case class Add(a: Expression[Int], b: Expression[Int]) extends Expression[Int]

  final case class BoolLiteral(b: Boolean) extends Expression[Boolean]

  final case class Or(a: Expression[Boolean], b: Expression[Boolean]) extends Expression[Boolean]
}
