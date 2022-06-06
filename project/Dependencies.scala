import sbt._

object Dependencies {
  object Versions {
    val cats = "2.7.0"
    val specs2 = "4.15.0"
  }

  val catsCore = "org.typelevel" %% "cats-core" % Versions.cats
  val specs2Core = "org.specs2" %% "specs2-core" % Versions.specs2
}
