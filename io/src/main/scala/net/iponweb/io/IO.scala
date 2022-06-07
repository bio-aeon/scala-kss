package net.iponweb.io

sealed abstract class IO[+A] {
  def flatMap[B](f: A => IO[B]): IO[B] = FlatMap(this, f)
}

final case class Pure[+A](x: A) extends IO[A]
final case class Delay[+A](thunk: () => A) extends IO[A]
final case class FlatMap[A, +B](ioa: IO[A], f: A => IO[B]) extends IO[B]

object IO {
  def pure[A](x: A): IO[A] = Pure(x)

  def delay[A](x: => A): IO[A] = Delay(() => x)
}
