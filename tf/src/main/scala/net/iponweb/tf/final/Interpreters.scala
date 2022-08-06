package net.iponweb.tf.`final`

object Interpreters {

  val expressionEval: Expression[Int] = new Expression[Int] {
    override def intLiteral(n: Int): Int = n

    override def negate(a: Int): Int = -a

    override def add(a: Int, b: Int): Int = a + b
  }

  val logicalEval: Logical[Boolean] = new Logical[Boolean] {
    override def boolLiteral(b: Boolean): Boolean = b

    override def or(a: Boolean, b: Boolean): Boolean = a || b
  }

  val divisionEval: Division[Int] = new Division[Int] {
    override def divide(a: Int, b: Int): Option[Int] =
      if (b == 0) {
        None
      } else {
        Some(a / b)
      }
  }

  val expressionShow: Expression[String] = new Expression[String] {
    override def intLiteral(n: Int): String = n.toString

    override def negate(a: String): String = s"-$a"

    override def add(a: String, b: String): String = s"($a + $b)"
  }

  val divisionShow: Division[String] = new Division[String] {
    override def divide(a: String, b: String): Option[String] = Some(s"($a / $b)")
  }
}
