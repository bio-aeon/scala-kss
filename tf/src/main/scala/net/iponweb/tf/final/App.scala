package net.iponweb.tf.`final`

import cats.Id
import net.iponweb.tf.`final`.Interpreters._

object App {

  def main(argv: Array[String]): Unit = {
    val evalResult = Program.run[Either[String, *], Int](expressionEval, divisionEval)
    val showResult = Program.run[Id, String](expressionShow, divisionShow)

    println(s"Eval: $evalResult")
    println(s"Show: $showResult")
  }
}
