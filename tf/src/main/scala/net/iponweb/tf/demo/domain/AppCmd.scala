package net.iponweb.tf.demo.domain

import java.nio.file.Path

sealed trait AppCmd extends Product with Serializable

object AppCmd {

  final case class Join(firstDir: Path, secondDir: Path, outputDir: Path) extends AppCmd

  final case class Unique(targetDir: Path, outputDir: Path) extends AppCmd

  case object Exit extends AppCmd
}
