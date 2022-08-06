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
  scalaVersion := "2.13.8",
  scalacOptions ++= commonScalacOptions,
  libraryDependencies += specs2Core % Test,
  addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.patch),
  addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
)

lazy val root = (project in file("."))
  .settings(name := "scala-kss", commonSettings)
  .aggregate(intro, cat, monad, io, tf)

lazy val intro = (project in file("intro"))
  .settings(commonSettings)

lazy val cat = (project in file("cat"))
  .settings(commonSettings)

lazy val monad = (project in file("monad"))
  .settings(commonSettings)
  .dependsOn(cat)

lazy val io = (project in file("io"))
  .settings(commonSettings)
  .dependsOn(monad)

lazy val tf = (project in file("tf"))
  .settings(commonSettings, libraryDependencies ++= Seq(catsCore))
