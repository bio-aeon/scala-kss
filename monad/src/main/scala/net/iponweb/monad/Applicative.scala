package net.iponweb.monad

import net.iponweb.cat.Functor

trait Applicative[F[_]] extends Functor[F] {
  def pure[A](x: A): F[A]

  def ap[A, B](ff: F[A => B])(fa: F[A]): F[B]

  def map[A, B](fa: F[A])(f: A => B): F[B] =
    ap(pure(f))(fa)

  def product[A, B](fa: F[A], fb: F[B]): F[(A, B)] =
    ap(map(fa)(a => (b: B) => (a, b)))(fb)

  def map2[A, B, C](fa: F[A], fb: F[B])(f: (A, B) => C): F[C] =
    map(product(fa, fb))(f.tupled)

  def map3[A, B, C, D](fa: F[A], fb: F[B], fc: F[C])(f: (A, B, C) => D): F[D] =
    map2(fa, product(fb, fc)) { case (a, (b, c)) => f(a, b, c) }
}
