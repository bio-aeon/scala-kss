package net.iponweb.tf.demo

import cats.effect.IO
import net.iponweb.tf.demo.routing.Routes
import net.iponweb.tf.demo.services.ETLService
import net.iponweb.tf.demo.utils.FilesManager

import scala.io.StdIn.readLine

object Program {
  def create: IO[Unit] = {
    val filesManager = FilesManager.create
    val etlService = ETLService.create(filesManager)
    val routes = Routes.create(etlService)
    processLoop(routes)
  }

  private def processLoop(routes: Routes): IO[Unit] =
    IO(readLine("Enter your command: "))
      .map(_.split("\\s+").toList)
      .map(CmdParsing.parse)
      .flatMap(
        _.fold(help => IO(println(help)).as(true), routes.handle)
          .ifM(processLoop(routes), IO(println("Bye")))
      )
}
