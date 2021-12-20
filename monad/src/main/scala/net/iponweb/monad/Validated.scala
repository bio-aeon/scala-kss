package net.iponweb.monad

import net.iponweb.monad.Validated.{Invalid, Valid}

sealed abstract class Validated[+E, +A] {
  def ap[EE >: E, B](f: Validated[EE, A => B])(implicit EE: Semigroup[EE]): Validated[EE, B] =
    (this, f) match {
      case (Valid(a), Valid(f)) => Valid(f(a))
      case (Invalid(e1), Invalid(e2)) => Invalid(EE.combine(e2, e1))
      case (e @ Invalid(_), _) => e
      case (_, e @ Invalid(_)) => e
    }
}

object Validated {
  final case class Valid[+A](a: A) extends Validated[Nothing, A]
  final case class Invalid[+E](e: E) extends Validated[E, Nothing]

  def valid[E, A](a: A): Validated[E, A] = Valid(a)

  def invalid[E, A](e: E): Validated[E, A] = Invalid(e)
}
