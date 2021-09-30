import Dependencies._

val commonScalacOptions = Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Xfatal-warnings",
  "-Ymacro-annotations"
)

val commonSettings = Seq(
  organization := "net.iponweb",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.13.6",
  scalacOptions ++= commonScalacOptions,
  addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.0" cross CrossVersion.patch),
  addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
)

lazy val root = (project in file("."))
  .settings(name := "scala-kss", commonSettings)
  .aggregate(intro)

lazy val intro = (project in file("intro"))
  .settings(commonSettings, libraryDependencies ++= Seq(specs2Core))
