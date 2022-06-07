package net.iponweb.io

import net.iponweb.io.instances.monadForIO
import net.iponweb.monad.syntax.monad._

class HttpClient {
  def post(url: String): String = "token"
}

class FileIndexClient {
  def fetch(token: String): List[String] =
    List("my_log.avro")
}

class GSClient {
  def download(path: String): Boolean = true
}

object Effectful {

  val httpClient = new HttpClient()
  val fileIndexClient = new FileIndexClient()
  val gsClient = new GSClient()

  def createAuthToken: IO[String] =
    showMsg("Creating an auth token") >> IO.delay(httpClient.post("auth-url"))

  def fetchLogs(token: String): IO[List[String]] =
    showMsg("Fetching logs") >> IO.delay(fileIndexClient.fetch(token))

  def downloadLog(path: String): IO[Boolean] =
    showMsg("Downloading log file") >> IO.delay(gsClient.download(path))

  def showMsg(msg: String): IO[Unit] =
    IO.delay(println(msg))
}
