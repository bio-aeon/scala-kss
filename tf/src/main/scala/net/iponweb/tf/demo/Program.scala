package net.iponweb.tf.demo

import cats.effect.Sync
import cats.mtl.{Local, Stateful}
import cats.syntax.flatMap._
import cats.syntax.functor._
import net.iponweb.tf.demo.domain.{AppCtx, LimitsState}
import net.iponweb.tf.demo.routing.Routes
import net.iponweb.tf.demo.services.ETLService
import net.iponweb.tf.demo.utils.{FilesManager, Logs}

import java.util.UUID
import scala.io.StdIn.readLine

object Program {
  def create[F[_]: Sync: Local[*[_], AppCtx]: Stateful[*[_], LimitsState]]: F[Unit] = {
    implicit val logs: Logs[F] = Logs.withCtx[F]
    val filesManager = FilesManager.create[F]
    val etlService = ETLService.create[F](filesManager)
    val routes = Routes.create[F](etlService)
    run(routes)
  }

  private def run[F[_]](routes: Routes[F])(implicit F: Sync[F], L: Local[F, AppCtx]) =
    F.delay(readLine("Type your name: ")).flatMap { userName =>
      F.delay(println(s"Hi, $userName!")) >> L
        .local(processLoop(routes))(_.copy(userName = userName))
    }

  private def processLoop[F[_]](routes: Routes[F])(implicit F: Sync[F],
                                                   L: Local[F, AppCtx]): F[Unit] =
    F.delay(readLine("Enter your command: "))
      .map(_.split("\\s+").toList)
      .map(CmdParsing.parse)
      .flatMap(
        _.fold(
          help => F.delay(println(help)).as(true),
          cmd => createTraceId.flatMap(id => L.local(routes.handle(cmd))(_.copy(traceId = id)))
        ).ifM(processLoop(routes), F.delay(println("Bye")))
      )

  private def createTraceId[F[_]](implicit F: Sync[F]): F[String] =
    F.delay(UUID.randomUUID().toString)
}
