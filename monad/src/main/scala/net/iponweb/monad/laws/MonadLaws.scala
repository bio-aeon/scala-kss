package net.iponweb.monad.laws

import net.iponweb.monad.Monad

object MonadLaws {
  def preservesLeftIdentity[F[_], A, B](a: A, f: A => F[B])(implicit F: Monad[F]): Boolean =
    F.flatMap(F.pure(a))(f) == f(a)

  def preservesRightIdentity[F[_], A](fa: F[A])(implicit F: Monad[F]): Boolean =
    F.flatMap(fa)(F.pure) == fa

  def preservesAssociativity[F[_], A, B, C](fa: F[A], f: A => F[B], g: B => F[C])(
    implicit F: Monad[F]
  ): Boolean =
    F.flatMap(F.flatMap(fa)(f))(g) == F.flatMap(fa)(a => F.flatMap(f(a))(g))
}
