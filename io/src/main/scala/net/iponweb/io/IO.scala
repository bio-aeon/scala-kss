package net.iponweb.io

abstract class IO[+A] { self: IO[A] =>
  def run: A

  def flatMap[B](f: A => IO[B]): IO[B] =
    new IO[B] {
      def run: B = f(self.run).run
    }
}

object IO {
  def pure[A](x: A): IO[A] = new IO[A] {
    def run: A = x
  }

  def delay[A](x: => A): IO[A] = new IO[A] {
    def run: A = x
  }
}
