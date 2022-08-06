package net.iponweb.tf.initial.tagless

import net.iponweb.tf.initial.tagless.Expression.{Add, BoolLiteral, IntLiteral, Negate, Or}

object Program {

  //Or(BoolLiteral(true), Add(IntLiteral(10), Negate(IntLiteral(5))))

  val run: Expression[Int] = Add(IntLiteral(10), Negate(IntLiteral(5)))
}
