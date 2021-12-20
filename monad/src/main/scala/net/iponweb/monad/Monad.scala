package net.iponweb.monad

import net.iponweb.cat.Functor

trait Monad[F[_]] extends Functor[F] {
  def pure[A](x: A): F[A]

  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  def map[A, B](fa: F[A])(f: A => B): F[B] =
    flatMap(fa)(a => pure(f(a)))
}
