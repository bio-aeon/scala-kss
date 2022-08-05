package net.iponweb.tf.demo

import zio.interop.catz._
import zio.{ExitCode, RIO, URIO, ZEnv}

object ZIOApp extends CatsApp {
  type RunF[A] = RIO[ZEnv, A]

  def run(args: List[String]): URIO[ZEnv, ExitCode] =
    Program.create[RunF].as(ExitCode.success).orDie
}
