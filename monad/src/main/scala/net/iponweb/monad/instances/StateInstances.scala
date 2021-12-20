package net.iponweb.monad.instances

import net.iponweb.monad.{Monad, State}

trait StateInstances {
  implicit def monadForState[S]: Monad[State[S, *]] =
    new Monad[State[S, *]] {
      def pure[A](x: A): State[S, A] = State(s => (s, x))

      def flatMap[A, B](fa: State[S, A])(f: A => State[S, B]): State[S, B] = fa.flatMap(f)
    }
}
