package net.iponweb.tf.demo

import cats.effect.{ExitCode, IO, IOApp}

object App extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    Program.create.as(ExitCode.Success)
}
