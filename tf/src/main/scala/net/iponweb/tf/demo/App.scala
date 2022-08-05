package net.iponweb.tf.demo

import cats.data.ReaderT
import cats.effect.{ExitCode, IO, IOApp}
import net.iponweb.tf.demo.domain.AppCtx

object App extends IOApp {
  type RunF[A] = ReaderT[IO, AppCtx, A]

  def run(args: List[String]): IO[ExitCode] =
    Program.create[RunF].run(AppCtx()).as(ExitCode.Success)
}
