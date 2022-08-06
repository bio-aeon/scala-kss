package net.iponweb.tf.initial.tagged

sealed trait Expression extends Product with Serializable

object Expression {

  final case class IntLiteral(n: Int) extends Expression

  final case class Negate(a: Expression) extends Expression

  final case class Add(a: Expression, b: Expression) extends Expression

  final case class BoolLiteral(b: Boolean) extends Expression

  final case class Or(a: Expression, b: Expression) extends Expression
}
