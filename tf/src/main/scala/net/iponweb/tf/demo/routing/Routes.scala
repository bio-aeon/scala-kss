package net.iponweb.tf.demo.routing

import cats.MonadError
import cats.mtl.Stateful
import cats.syntax.applicative._
import cats.syntax.applicativeError._
import cats.syntax.flatMap._
import cats.syntax.functor._
import net.iponweb.tf.demo.domain.AppCmd.{Exit, Join, Unique}
import net.iponweb.tf.demo.domain.{AppCmd, LimitsState}
import net.iponweb.tf.demo.services.ETLService
import net.iponweb.tf.demo.utils.{Logger, Logs}

trait Routes[F[_]] {
  def handle(cmd: AppCmd): F[Boolean]
}

object Routes {

  val ReadsLimit = 20
  val WritesLimit = 3

  def create[F[_]: MonadError[*[_], Throwable]: Stateful[*[_], LimitsState]](
    etlService: ETLService[F]
  )(implicit logs: Logs[F]): Routes[F] = {
    val logger = logs.forService[Routes[F]]
    new Impl[F](logger, etlService)
  }

  private final class Impl[F[_]: MonadError[*[_], Throwable]](
    logger: Logger[F],
    etlService: ETLService[F]
  )(implicit S: Stateful[F, LimitsState])
      extends Routes[F] {

    override def handle(cmd: AppCmd): F[Boolean] =
      (cmd match {
        case Join(firstDir, secondDir, outputDir) =>
          checkLimits {
            etlService.joinLogsByRequestId(firstDir, secondDir, outputDir)
          }
        case Unique(targetDir, outputDir) =>
          checkLimits {
            etlService.extractUniqueClientIds(targetDir, outputDir)
          }
        case Exit => false.pure[F]
      }).handleErrorWith(
        err => logger.error(s"Failed to handle the command: ${err.getMessage}").as(true)
      )

    private def checkLimits[A](fa: F[A]): F[Boolean] =
      S.get.flatMap { s =>
        if (s.reads < ReadsLimit && s.writes < WritesLimit) {
          fa.as(true)
        } else {
          logger.error("Read/write limits exceeded").as(false)
        }
      }
  }
}
