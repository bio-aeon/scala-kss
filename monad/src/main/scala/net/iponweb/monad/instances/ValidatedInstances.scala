package net.iponweb.monad.instances

import net.iponweb.monad.{Applicative, Semigroup, Validated}

trait ValidatedInstances {
  implicit def applicativeForValidated[E: Semigroup]: Applicative[Validated[E, *]] =
    new Applicative[Validated[E, *]] {
      def pure[A](x: A): Validated[E, A] = Validated.valid(x)

      def ap[A, B](ff: Validated[E, A => B])(fa: Validated[E, A]): Validated[E, B] = fa.ap(ff)
    }
}
