package net.iponweb.tf.`final`

object Program {

  def run[A](expression: Expression[A], division: Division[A]): Option[A] = {
    import division._
    import expression._

    val divResult_? = divide(add(intLiteral(35), negate(intLiteral(5))), intLiteral(2))
    divResult_?.map { divResult =>
      add(divResult, intLiteral(10))
    }
  }
}
