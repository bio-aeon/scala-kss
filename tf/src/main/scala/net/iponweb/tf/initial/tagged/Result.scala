package net.iponweb.tf.initial.tagged

sealed trait Result

object Result {

  final case class IntResult(n: Int) extends Result

  final case class BoolResult(b: Boolean) extends Result
}
