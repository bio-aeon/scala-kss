package net.iponweb.monad.syntax

import net.iponweb.monad.Monad

trait MonadSyntax {
  final implicit def syntaxMonad[F[_], A](fa: F[A]): MonadOps[F, A] =
    new MonadOps(fa)
}

private[syntax] final class MonadOps[F[_], A](private val fa: F[A]) extends AnyVal {
  def map[B](f: A => B)(implicit F: Monad[F]): F[B] = F.map(fa)(f)

  def ifM[B](ifTrue: => F[B], ifFalse: => F[B])(implicit F: Monad[F], ev: A <:< Boolean): F[B] =
    F.flatMap(fa)(x => if (ev(x)) ifTrue else ifFalse)

  def >>[B](fb: => F[B])(implicit F: Monad[F]): F[B] = F.flatMap(fa)(_ => fb)
}
