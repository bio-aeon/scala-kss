package net.iponweb.tf.`final`

import net.iponweb.tf.`final`.Interpreters._

object App {

  def main(argv: Array[String]): Unit = {
    val evalResult = Program.run(expressionEval, divisionEval)
    val showResult = Program.run(expressionShow, divisionShow)

    println(s"Eval: $evalResult")
    println(s"Show: $showResult")
  }
}
