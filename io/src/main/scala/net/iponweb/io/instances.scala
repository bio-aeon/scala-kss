package net.iponweb.io

import net.iponweb.monad.Monad

object instances {
  implicit val monadForIO: Monad[IO] = new Monad[IO] {

    def pure[A](x: A): IO[A] = IO.pure(x)

    def flatMap[A, B](fa: IO[A])(f: A => IO[B]): IO[B] = fa.flatMap(f)
  }
}
