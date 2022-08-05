package net.iponweb.tf.demo.utils

import cats.effect.Sync
import cats.mtl.Ask
import net.iponweb.tf.demo.domain.AppCtx
import org.slf4j.LoggerFactory

import scala.reflect.ClassTag

trait Logs[F[_]] {
  def forService[Svc](implicit ct: ClassTag[Svc]): Logger[F]
}

object Logs {

  def withCtx[F[_]: Sync: Ask[*[_], AppCtx]]: Logs[F] = new Logs[F] {
    override def forService[Svc](implicit ct: ClassTag[Svc]): Logger[F] =
      Logger.create[F](LoggerFactory.getLogger(ct.runtimeClass.getName))
  }
}
