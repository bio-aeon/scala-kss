package net.iponweb.tf.demo.utils

import cats.effect.Sync
import org.slf4j.{LoggerFactory, Logger => Slf4jLogger}

import scala.reflect.ClassTag

trait Logger[F[_]] {
  def debug(message: String): F[Unit]

  def info(message: String): F[Unit]

  def warn(message: String): F[Unit]

  def error(message: String): F[Unit]

  def error(message: String, cause: Throwable): F[Unit]
}

object Logger {

  def create[F[_]: Sync, Svc](implicit ct: ClassTag[Svc]): Logger[F] =
    new Impl[F](LoggerFactory.getLogger(ct.runtimeClass.getName))

  private final class Impl[F[_]](logger: Slf4jLogger)(implicit F: Sync[F]) extends Logger[F] {
    override def debug(message: String): F[Unit] = F.delay(logger.debug(message))

    override def info(message: String): F[Unit] = F.delay(logger.info(message))

    override def warn(message: String): F[Unit] = F.delay(logger.warn(message))

    override def error(message: String): F[Unit] = F.delay(logger.error(message))

    override def error(message: String, cause: Throwable): F[Unit] =
      F.delay(logger.error(message, cause))
  }
}
