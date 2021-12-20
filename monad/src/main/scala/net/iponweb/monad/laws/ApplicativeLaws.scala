package net.iponweb.monad.laws

import net.iponweb.monad.Applicative
import net.iponweb.monad.syntax.applicative._

object ApplicativeLaws {
  def preservesIdentity[F[_], A](fa: F[A])(implicit F: Applicative[F]): Boolean =
    F.pure((a: A) => a).ap(fa) == fa

  def preservesComposition[F[_], A, B, C](fa: F[A], fab: F[A => B], fbc: F[B => C])(
    implicit F: Applicative[F]
  ): Boolean = {
    val compose: (B => C) => (A => B) => (A => C) = _.compose
    F.pure(compose).ap(fbc).ap(fab).ap(fa) == fbc.ap(fab.ap(fa))
  }

  def preservesHomomorphism[F[_], A, B](a: A, f: A => B)(implicit F: Applicative[F]): Boolean =
    F.pure(f).ap(F.pure(a)) == F.pure(f(a))

  def preservesInterchange[F[_], A, B](a: A, ff: F[A => B])(implicit F: Applicative[F]): Boolean =
    ff.ap(F.pure(a)) == F.pure((f: A => B) => f(a)).ap(ff)
}
