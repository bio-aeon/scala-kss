package net.iponweb.monad

import net.iponweb.cat.Functor

trait Applicative[F[_]] extends Functor[F] {
  def pure[A](x: A): F[A]

  def ap[A, B](ff: F[A => B])(fa: F[A]): F[B]

  def map[A, B](fa: F[A])(f: A => B): F[B] =
    ap(pure(f))(fa)
}
