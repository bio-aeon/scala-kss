package net.iponweb.tf.`final`

object Program {

  def run[F[_], A](expression: Expression[F, A], division: Division[F, A]): F[A] = {
    import division._
    import expression._

    val divResult = divide(add(intLiteral(35), negate(intLiteral(5))), intLiteral(2))
    add(divResult, intLiteral(10))
  }
}
