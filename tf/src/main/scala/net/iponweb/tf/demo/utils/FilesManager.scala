package net.iponweb.tf.demo.utils

import cats.effect.{IO, Resource}

import java.io._
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path}
import scala.jdk.CollectionConverters._

trait FilesManager {
  def listFiles(dir: Path, extensions: List[String]): IO[List[File]]

  def readFile(file: File): IO[List[String]]

  def writeToFile(content: List[String], file: File): IO[Unit]
}

object FilesManager {

  def create: FilesManager =
    new Impl

  private final class Impl extends FilesManager {
    override def listFiles(dir: Path, extensions: List[String]): IO[List[File]] =
      IO(Files.newDirectoryStream(dir))
        .map(_.iterator.asScala)
        .map(_.filter(x => extensions.exists(x.toString.endsWith)).map(_.toFile))
        .map(_.toList)

    override def readFile(file: File): IO[List[String]] =
      Resource
        .fromAutoCloseable(IO(new BufferedInputStream(new FileInputStream(file))))
        .use(is => IO(is.readAllBytes))
        .map(new String(_, StandardCharsets.UTF_8))
        .map(_.split("\n").toList)

    override def writeToFile(content: List[String], file: File): IO[Unit] =
      Resource
        .fromAutoCloseable(IO(new BufferedWriter(new FileWriter(file))))
        .use(bw => IO(bw.write(content.mkString("\n"))))
  }
}
