import sbt._

object Dependencies {
  object Versions {
    val logback = "1.2.11"
    val decline = "2.3.0"
    val circe = "0.14.2"
    val specs2 = "4.15.0"
    val catsEffect = "3.3.14"
    val zioInterop = "3.2.9.1"
  }

  val logbackClassic = "ch.qos.logback" % "logback-classic" % Versions.logback
  val decline = "com.monovore" %% "decline" % Versions.decline
  val circeGeneric = "io.circe" %% "circe-generic" % Versions.circe
  val circeParser = "io.circe" %% "circe-parser" % Versions.circe
  val catsEffect = "org.typelevel" %% "cats-effect" % Versions.catsEffect
  val zioInterop = "dev.zio" %% "zio-interop-cats" % Versions.zioInterop
  val specs2Core = "org.specs2" %% "specs2-core" % Versions.specs2
}
