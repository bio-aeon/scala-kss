package net.iponweb.monad

import net.iponweb.monad.instances.state._
import net.iponweb.monad.syntax.monad._
import org.specs2.mutable.Specification

class HttpClient {
  def sendRequest(req: String): State[Int, Unit] =
    State(s => (s + 1, (/*actual call*/ )))
}

class MyProgram(httpClient: HttpClient) {
  def main(): State[Int, Unit] = {
    val req = "Hi"
    for {
      _ <- httpClient.sendRequest(req)
      req2 = s"$req$req"
      _ <- httpClient.sendRequest(req2)
      req3 = s"$req$req$req"
      _ <- httpClient.sendRequest(req3)
    } yield ()
  }
}

class StateSpec extends Specification {

  "State should" >> {

    "remember the number of http calls" >> {
      val httpClient = new HttpClient()
      val program = new MyProgram(httpClient)

      val (state, _) = program.main().run(0)
      state mustEqual 3
    }
  }
}
