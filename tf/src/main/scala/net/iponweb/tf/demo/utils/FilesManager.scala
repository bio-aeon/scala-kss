package net.iponweb.tf.demo.utils

import cats.effect.{Resource, Sync}
import cats.syntax.functor._

import java.io._
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path}
import scala.jdk.CollectionConverters._

trait FilesManager[F[_]] {
  def listFiles(dir: Path, extensions: List[String]): F[List[File]]

  def readFile(file: File): F[List[String]]

  def writeToFile(content: List[String], file: File): F[Unit]
}

object FilesManager {

  def create[F[_]: Sync]: FilesManager[F] =
    new Impl[F]

  private final class Impl[F[_]](implicit F: Sync[F]) extends FilesManager[F] {
    override def listFiles(dir: Path, extensions: List[String]): F[List[File]] =
      F.delay(Files.newDirectoryStream(dir))
        .map(_.iterator.asScala)
        .map(_.filter(x => extensions.exists(x.toString.endsWith)).map(_.toFile))
        .map(_.toList)

    override def readFile(file: File): F[List[String]] =
      Resource
        .fromAutoCloseable(F.delay(new BufferedInputStream(new FileInputStream(file))))
        .use(is => F.delay(is.readAllBytes))
        .map(new String(_, StandardCharsets.UTF_8))
        .map(_.split("\n").toList)

    override def writeToFile(content: List[String], file: File): F[Unit] =
      Resource
        .fromAutoCloseable(F.delay(new BufferedWriter(new FileWriter(file))))
        .use(bw => F.delay(bw.write(content.mkString("\n"))))
  }
}
