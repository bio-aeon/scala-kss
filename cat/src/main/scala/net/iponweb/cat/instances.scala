package net.iponweb.cat

object instances {
  implicit val functorForList: Functor[List] = new Functor[List] {
    def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
  }

  implicit val functorForOption: Functor[Option] = new Functor[Option] {
    def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)
  }

  implicit def functorForEither[E]: Functor[Either[E, *]] =
    new Functor[Either[E, *]] {
      def map[A, B](fa: Either[E, A])(f: A => B): Either[E, B] = fa.map(f)
    }
}
