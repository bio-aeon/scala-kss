package net.iponweb.monad.instances

import net.iponweb.monad.Monad

import scala.util.{Success, Try}

trait TryInstances {
  implicit val monadForTry: Monad[Try] =
    new Monad[Try] {
      def pure[A](x: A): Try[A] = Success(x)

      def flatMap[A, B](fa: Try[A])(f: A => Try[B]): Try[B] = fa.flatMap(f)
    }
}
