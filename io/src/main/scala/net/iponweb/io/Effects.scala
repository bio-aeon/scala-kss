package net.iponweb.io

class HttpClient {
  def post(url: String): String = "token"
}

class FileIndexClient {
  def fetch(token: String): List[String] = {
    List("my_log.avro")
  }
}

class GSClient {
  def download(path: String): Unit = ()
}

object SideEffects {

  val httpClient = new HttpClient()
  val fileIndexClient = new FileIndexClient()
  val gsClient = new GSClient()

  def createAuthToken: String = {
    showMsg("Creating an auth token")
    httpClient.post("auth-url")
  }

  def fetchLogs(token: String): List[String] = {
    showMsg("Fetching logs")
    fileIndexClient.fetch(token)
  }

  def downloadLog(path: String): Unit = {
    showMsg("Downloading log file")
    gsClient.download(path)
  }

  def showMsg(msg: String): Unit = {
    println(msg)
  }
}
