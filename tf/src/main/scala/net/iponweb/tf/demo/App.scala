package net.iponweb.tf.demo

import cats.data.{ReaderT, StateT}
import cats.effect.{ExitCode, IO, IOApp}
import net.iponweb.tf.demo.domain.{AppCtx, LimitsState}

object App extends IOApp {
  type RunF[A] = ReaderT[StateT[IO, LimitsState, *], AppCtx, A]

  def run(args: List[String]): IO[ExitCode] =
    Program.create[RunF].run(AppCtx()).run(LimitsState()).as(ExitCode.Success)
}
