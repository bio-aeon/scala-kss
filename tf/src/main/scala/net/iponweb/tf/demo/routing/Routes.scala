package net.iponweb.tf.demo.routing

import cats.ApplicativeError
import cats.effect.Sync
import cats.syntax.applicative._
import cats.syntax.applicativeError._
import cats.syntax.functor._
import net.iponweb.tf.demo.domain.AppCmd
import net.iponweb.tf.demo.domain.AppCmd.{Exit, Join, Unique}
import net.iponweb.tf.demo.services.ETLService
import net.iponweb.tf.demo.utils.Logger

trait Routes[F[_]] {
  def handle(cmd: AppCmd): F[Boolean]
}

object Routes {

  def create[F[_]: Sync](etlService: ETLService[F]): Routes[F] = {
    val logger = Logger.create[F, Routes[F]]
    new Impl[F](logger, etlService)
  }

  private final class Impl[F[_]: ApplicativeError[*[_], Throwable]](logger: Logger[F],
                                                                    etlService: ETLService[F])
      extends Routes[F] {

    override def handle(cmd: AppCmd): F[Boolean] =
      (cmd match {
        case Join(firstDir, secondDir, outputDir) =>
          etlService.joinLogsByRequestId(firstDir, secondDir, outputDir).as(true)
        case Unique(targetDir, outputDir) =>
          etlService.extractUniqueClientIds(targetDir, outputDir).as(true)
        case Exit => false.pure[F]
      }).handleErrorWith(
        err => logger.error(s"Failed to handle the command: ${err.getMessage}").as(true)
      )
  }
}
