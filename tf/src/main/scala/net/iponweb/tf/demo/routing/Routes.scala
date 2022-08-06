package net.iponweb.tf.demo.routing

import cats.effect.IO
import net.iponweb.tf.demo.domain.AppCmd
import net.iponweb.tf.demo.domain.AppCmd.{Exit, Join, Unique}
import net.iponweb.tf.demo.services.ETLService
import net.iponweb.tf.demo.utils.Logger

trait Routes {
  def handle(cmd: AppCmd): IO[Boolean]
}

object Routes {

  def create(etlService: ETLService): Routes = {
    val logger = Logger.create[ETLService]
    new Impl(logger, etlService)
  }

  private final class Impl(logger: Logger, etlService: ETLService) extends Routes {

    override def handle(cmd: AppCmd): IO[Boolean] =
      (cmd match {
        case Join(firstDir, secondDir, outputDir) =>
          etlService.joinLogsByRequestId(firstDir, secondDir, outputDir).as(true)
        case Unique(targetDir, outputDir) =>
          etlService.extractUniqueClientIds(targetDir, outputDir).as(true)
        case Exit => IO.pure(false)
      }).handleErrorWith(
        err => logger.error(s"Failed to handle the command: ${err.getMessage}").as(true)
      )
  }
}
