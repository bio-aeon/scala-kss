package net.iponweb.tf.initial.tagless

object App {

  def main(argv: Array[String]): Unit =
    println(Interpreters.eval(Program.run))
}
