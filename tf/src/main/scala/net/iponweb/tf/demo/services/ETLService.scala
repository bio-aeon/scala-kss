package net.iponweb.tf.demo.services

import cats.MonadError
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.syntax.traverse._
import io.circe.Decoder
import io.circe.parser.decode
import io.circe.syntax._
import net.iponweb.tf.demo.domain.{JoinResult, RecordWithClientId, RecordWithRequestId}
import net.iponweb.tf.demo.utils.{FilesManager, Logger, Logs}

import java.nio.file.Path

trait ETLService[F[_]] {
  def joinLogsByRequestId(firstDir: Path, secondDir: Path, outputDir: Path): F[Unit]

  def extractUniqueClientIds(targetDir: Path, outputDir: Path): F[Unit]
}

object ETLService {

  val JoinOutputFile = "join-output.txt"
  val UniqueOutputFile = "unique-output.txt"

  def create[F[_]: MonadError[*[_], Throwable]](
    filesManager: FilesManager[F]
  )(implicit logs: Logs[F]): ETLService[F] = {
    val logger = logs.forService[ETLService[F]]
    new Impl(logger, filesManager)
  }

  private final class Impl[F[_]](logger: Logger[F], filesManager: FilesManager[F])(
    implicit F: MonadError[F, Throwable]
  ) extends ETLService[F] {
    override def joinLogsByRequestId(firstDir: Path, secondDir: Path, outputDir: Path): F[Unit] =
      for {
        firstRecords <- loadGroupedRecords[RecordWithRequestId, String](firstDir, _.requestId)
        secondRecords <- loadGroupedRecords[RecordWithRequestId, String](secondDir, _.requestId)
        _ <- logger.info("Match logs with the same request id")
        results = firstRecords.toList
          .flatMap {
            case (requestId, record) =>
              secondRecords
                .get(requestId)
                .map(
                  x =>
                    JoinResult(
                      requestId,
                      Map(
                        firstDir.getFileName.toString -> record.map(_.data),
                        secondDir.getFileName.toString -> x.map(_.data)
                      )
                  )
                )
          }
          .map(_.asJson.noSpaces)
        _ <- logger.info(s"Save join results of length ${results.length} to file")
        _ <- filesManager.writeToFile(results, outputDir.resolve(JoinOutputFile).toFile)
      } yield ()

    override def extractUniqueClientIds(targetDir: Path, outputDir: Path): F[Unit] =
      for {
        records <- loadGroupedRecords[RecordWithClientId, String](targetDir, _.clientId)
        _ <- logger.info("Extract unique client ids")
        clientIds = records.keySet
        _ <- logger.info(s"Save ${clientIds.size} unique client ids to file")
        _ <- filesManager.writeToFile(clientIds.toList, outputDir.resolve(UniqueOutputFile).toFile)
      } yield ()

    private def loadGroupedRecords[A: Decoder, K](dir: Path, groupBy: A => K): F[Map[K, List[A]]] =
      for {
        _ <- logger.info(s"Read logs list from ${dir.getFileName} directory")
        logsList <- filesManager.listFiles(dir, List(".log"))
        logsContent <- logsList.flatTraverse(filesManager.readFile)
        _ <- logger.info(s"Decode content of logs retrieved from ${dir.getFileName} directory")
        records <- logsContent
          .traverse(x => decode[A](x).fold(err => F.raiseError[A](err), F.pure))
          .map(_.groupBy(groupBy))
      } yield records
  }
}
