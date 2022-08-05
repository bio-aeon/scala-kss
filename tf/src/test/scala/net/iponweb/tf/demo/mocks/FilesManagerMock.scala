package net.iponweb.tf.demo.mocks

import cats.Applicative
import cats.mtl.Stateful
import net.iponweb.tf.demo.utils.FilesManager

import java.io.File
import java.nio.file.Path

object FilesManagerMock {

  def create[F[_]](
    listFilesResult: List[File],
    readFileResult: List[String]
  )(implicit F: Applicative[F], S: Stateful[F, Int]): FilesManager[F] =
    new FilesManager[F] {
      override def listFiles(dir: Path, extensions: List[String]): F[List[File]] =
        F.pure(listFilesResult)

      override def readFile(file: File): F[List[String]] = F.pure(readFileResult)

      override def writeToFile(content: List[String], file: File): F[Unit] =
        S.modify(_ + content.length)
    }
}
