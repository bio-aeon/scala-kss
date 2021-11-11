package net.iponweb.cat

object FunctorLaws {
  def preservesIdentity[F[_], A](fa: F[A])(implicit F: Functor[F]): Boolean =
    F.map(fa)(identity) == identity(fa)

  def preservesComposition[F[_], A, B, C](fa: F[A], f: A => B, g: B => C)(
    implicit F: Functor[F]
  ): Boolean = {
    val case0 = F.map(_: F[A])(g compose f)
    val case1 = (F.map(_: F[B])(g)) compose (F.map(_: F[A])(f))

    case0(fa) == case1(fa)
  }
}
