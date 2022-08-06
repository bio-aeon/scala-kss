package net.iponweb.tf.demo.utils

import cats.effect.IO
import org.slf4j.{LoggerFactory, Logger => Slf4jLogger}

import scala.reflect.ClassTag

trait Logger {
  def debug(message: String): IO[Unit]

  def info(message: String): IO[Unit]

  def warn(message: String): IO[Unit]

  def error(message: String): IO[Unit]

  def error(message: String, cause: Throwable): IO[Unit]
}

object Logger {

  def create[Svc](implicit ct: ClassTag[Svc]): Logger =
    new Impl(LoggerFactory.getLogger(ct.runtimeClass.getName))

  private final class Impl(logger: Slf4jLogger) extends Logger {
    override def debug(message: String): IO[Unit] = IO(logger.debug(message))

    override def info(message: String): IO[Unit] = IO(logger.info(message))

    override def warn(message: String): IO[Unit] = IO(logger.warn(message))

    override def error(message: String): IO[Unit] = IO(logger.error(message))

    override def error(message: String, cause: Throwable): IO[Unit] =
      IO(logger.error(message, cause))
  }
}
