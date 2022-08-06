package net.iponweb.tf.initial.tagless

import net.iponweb.tf.initial.tagless.Expression._

object Interpreters {

  def eval[A](exp: Expression[A]): A =
    exp match {
      case IntLiteral(n) => n
      case Negate(a) => -eval(a)
      case Add(a, b) => eval(a) + eval(b)
      case BoolLiteral(b) => b
      case Or(a, b) => eval(a) || eval(b)
    }
}
