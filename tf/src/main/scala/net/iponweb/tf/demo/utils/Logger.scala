package net.iponweb.tf.demo.utils

import cats.effect.Sync
import cats.mtl.Ask
import cats.syntax.flatMap._
import net.iponweb.tf.demo.domain.AppCtx
import org.slf4j.{Logger => Slf4jLogger}

trait Logger[F[_]] {
  def debug(message: String): F[Unit]

  def info(message: String): F[Unit]

  def warn(message: String): F[Unit]

  def error(message: String): F[Unit]

  def error(message: String, cause: Throwable): F[Unit]
}

object Logger {

  def create[F[_]: Sync: Ask[*[_], AppCtx]](logger: Slf4jLogger): Logger[F] =
    new Impl[F](logger)

  private final class Impl[F[_]](logger: Slf4jLogger)(implicit F: Sync[F], A: Ask[F, AppCtx])
      extends Logger[F] {
    override def debug(message: String): F[Unit] = logWithModifiedMessage(message, logger.debug)

    override def info(message: String): F[Unit] = logWithModifiedMessage(message, logger.info)

    override def warn(message: String): F[Unit] = logWithModifiedMessage(message, logger.warn)

    override def error(message: String): F[Unit] = logWithModifiedMessage(message, logger.error)

    override def error(message: String, cause: Throwable): F[Unit] =
      logWithModifiedMessage(message, logger.error(_, cause))

    private def logWithModifiedMessage(message: String, log: String => Unit): F[Unit] =
      A.ask.flatMap { ctx =>
        F.delay(log(s"[${ctx.userName}] [${ctx.traceId}] $message"))
      }
  }
}
