package net.iponweb.tf.demo.mocks

import cats.Applicative
import net.iponweb.tf.demo.utils.{Logger, Logs}

import scala.reflect.ClassTag

object LogsMock {
  
  def create[F[_]](implicit F: Applicative[F]): Logs[F] = new Logs[F] {
    override def forService[Svc](implicit ct: ClassTag[Svc]): Logger[F] =
      new Logger[F] {
        override def debug(message: String): F[Unit] = F.unit

        override def info(message: String): F[Unit] = F.unit

        override def warn(message: String): F[Unit] = F.unit

        override def error(message: String): F[Unit] = F.unit

        override def error(message: String, cause: Throwable): F[Unit] = F.unit
      }
  }
}
