import sbt._

object Dependencies {
  object Versions {
    val specs2 = "4.12.12"
  }

  val specs2Core = "org.specs2" %% "specs2-core" % Versions.specs2
}
