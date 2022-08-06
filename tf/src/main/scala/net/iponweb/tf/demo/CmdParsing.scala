package net.iponweb.tf.demo

import cats.syntax.apply._
import com.monovore.decline.{Command, Help, Opts}
import net.iponweb.tf.demo.domain.AppCmd
import net.iponweb.tf.demo.domain.AppCmd.{Exit, Join, Unique}

import java.nio.file.Path

object CmdParsing {

  val outputDir: Opts[Path] = Opts
    .option[Path]("output-dir", "Directory for output", metavar = "path")
    .validate("Output path must be absolute")(_.isAbsolute)

  val joinCmd: Command[Join] =
    Command(name = "join", header = "Join logs of two services by request id") {
      val firstDir = Opts.option[Path]("first-dir", "First directory with logs", metavar = "path")
      val secondDir =
        Opts.option[Path]("second-dir", "Second directory with logs", metavar = "path")

      (firstDir, secondDir, outputDir).mapN(Join)
    }

  val uniqueCmd: Command[Unique] =
    Command(name = "unique", header = "Extract all unique client ids") {
      val targetDir =
        Opts.option[Path]("target-dir", "Target directory with logs", metavar = "path")

      (targetDir, outputDir).mapN(Unique)
    }

  val exitCmd: Command[Exit.type] = Command(name = "exit", header = "Exit program")(Opts(Exit))

  def parse(args: List[String]): Either[Help, AppCmd] =
    Command("DemoApp", "Command is required")(Opts.subcommands(joinCmd, uniqueCmd, exitCmd))
      .parse(args)

}
