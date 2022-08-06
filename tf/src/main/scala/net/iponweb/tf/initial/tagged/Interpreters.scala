package net.iponweb.tf.initial.tagged

import net.iponweb.tf.initial.tagged.Expression._
import net.iponweb.tf.initial.tagged.Result.{BoolResult, IntResult}

object Interpreters {

  def eval(exp: Expression): Option[Result] =
    exp match {
      case IntLiteral(n) => Some(IntResult(n))
      case Negate(a) =>
        eval(a).flatMap {
          case IntResult(n) => Some(IntResult(-n))
          case BoolResult(_) => None
        }
      case Add(a, b) =>
        (eval(a), eval(b)) match {
          case (Some(IntResult(na)), Some(IntResult(nb))) => Some(IntResult(na + nb))
          case _ => None
        }
      case BoolLiteral(b) => Some(BoolResult(b))
      case Or(a, b) =>
        (eval(a), eval(b)) match {
          case (Some(BoolResult(ba)), Some(BoolResult(bb))) => Some(BoolResult(ba || bb))
          case _ => None
        }
    }
}
