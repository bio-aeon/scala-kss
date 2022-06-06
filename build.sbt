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
  addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.patch),
  addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
)

lazy val root = (project in file("."))
  .settings(name := "scala-kss", commonSettings)
  .aggregate(intro, cat, monad, io)

lazy val intro = (project in file("intro"))
  .settings(commonSettings, libraryDependencies ++= Seq(specs2Core % Test))

lazy val cat = (project in file("cat"))
  .settings(commonSettings, libraryDependencies ++= Seq(specs2Core % Test))

lazy val monad = (project in file("monad"))
  .settings(commonSettings, libraryDependencies ++= Seq(specs2Core % Test))
  .dependsOn(cat)

lazy val io = (project in file("io"))
  .settings(commonSettings, libraryDependencies ++= Seq(catsCore))
