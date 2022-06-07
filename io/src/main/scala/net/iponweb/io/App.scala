package net.iponweb.io

object App {
  def main(args: Array[String]): Unit = {
    val token = SideEffects.createAuthToken
    val firstLog = SideEffects.fetchLogs(token).head
    val downloaded = SideEffects.downloadLog(firstLog)
    if (downloaded) {
      SideEffects.showMsg("Successful log download.")
    } else {
      SideEffects.showMsg("Failed to download a log.")
    }

    SideEffects.showMsg("All done.")
  }
}
