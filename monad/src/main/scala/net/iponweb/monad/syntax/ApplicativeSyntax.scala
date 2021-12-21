package net.iponweb.monad.syntax

import net.iponweb.monad.Applicative

trait ApplicativeSyntax {
  final implicit def syntaxApplicative[F[_], A](fa: F[A]): ApplicativeOps[F, A] =
    new ApplicativeOps(fa)
}

private[syntax] final class ApplicativeOps[F[_], A](private val fa: F[A]) extends AnyVal {
  def ap[B, C](fb: F[B])(implicit F: Applicative[F], ev: A <:< (B => C)): F[C] =
    F.ap(fa.asInstanceOf[F[B => C]])(fb)
}
