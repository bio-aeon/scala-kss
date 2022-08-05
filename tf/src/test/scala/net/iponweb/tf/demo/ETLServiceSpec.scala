package net.iponweb.tf.demo

import cats.Id
import cats.data.{EitherT, StateT}
import net.iponweb.tf.demo.mocks.{FilesManagerMock, LogsMock}
import net.iponweb.tf.demo.services.ETLService
import net.iponweb.tf.demo.utils.Logs
import org.specs2.mutable.Specification

import java.io.File
import java.nio.file.Path

class ETLServiceSpec extends Specification {
  type RunF[A] = StateT[EitherT[Id, Throwable, *], Int, A]

  implicit val logs: Logs[RunF] = LogsMock.create[RunF]

  "ETLService should" >> {

    "write correct count of joined log records" >> {
      val files = List("1.log", "2.log").map(new File(_))
      val contents = List("""{"requestId": "r1"}""", """{"requestId": "r2"}""")

      val filesManager = FilesManagerMock.create[RunF](files, contents)
      val etlService = ETLService.create[RunF](filesManager)

      etlService
        .joinLogsByRequestId(Path.of("first"), Path.of("second"), Path.of("output"))
        .runS(0)
        .value must beRight(2)
    }
  }
}
