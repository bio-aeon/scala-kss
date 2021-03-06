package net.iponweb.io

import net.iponweb.io.instances.monadForIO
import net.iponweb.monad.syntax.monad._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object App {
  def main(args: Array[String]): Unit =
    Await.ready(Interpreters.runIOAsync(program), Duration.Inf)

  private[io] def program: IO[Unit] =
    for {
      token <- Effectful.createAuthToken
      firstLog <- Effectful.fetchLogs(token).map(_.head)
      _ <- Effectful
        .downloadLog(firstLog)
        .ifM(
          Effectful.showMsg("Successful log download."),
          Effectful.showMsg("Failed to download a log.")
        )
      _ <- Effectful.showMsg("All done.")
    } yield ()
}
