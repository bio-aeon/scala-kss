package net.iponweb.tf.initial.tagged

import net.iponweb.tf.initial.tagged.Result.{BoolResult, IntResult}

object App {

  def main(argv: Array[String]): Unit = {
    val result = Interpreters.eval(Program.run) match {
      case Some(IntResult(n)) => n.toString
      case Some(BoolResult(b)) => b.toString
      case None => "error"
    }

    println(result)
  }
}
