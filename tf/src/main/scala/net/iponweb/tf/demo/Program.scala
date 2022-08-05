package net.iponweb.tf.demo

import cats.effect.Sync
import cats.syntax.flatMap._
import cats.syntax.functor._
import net.iponweb.tf.demo.routing.Routes
import net.iponweb.tf.demo.services.ETLService
import net.iponweb.tf.demo.utils.FilesManager

import scala.io.StdIn.readLine

object Program {
  def create[F[_]: Sync]: F[Unit] = {
    val filesManager = FilesManager.create[F]
    val etlService = ETLService.create[F](filesManager)
    val routes = Routes.create[F](etlService)
    processLoop(routes)
  }

  private def processLoop[F[_]](routes: Routes[F])(implicit F: Sync[F]): F[Unit] =
    F.delay(readLine("Enter your command: "))
      .map(_.split("\\s+").toList)
      .map(CmdParsing.parse)
      .flatMap(
        _.fold(help => F.delay(println(help)).as(true), routes.handle)
          .ifM(processLoop(routes), F.delay(println("Bye")))
      )
}
