package net.iponweb.tf.initial.tagged

import net.iponweb.tf.initial.tagged.Expression._

object Program {

  val run: Expression = Or(BoolLiteral(true), Add(IntLiteral(10), Negate(IntLiteral(5))))
}
