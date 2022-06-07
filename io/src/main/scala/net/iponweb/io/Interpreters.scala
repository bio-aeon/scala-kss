package net.iponweb.io

import scala.annotation.tailrec

object Interpreters {

  @tailrec
  def runIO[A](io: IO[A]): A =
    io match {
      case Pure(x) => x
      case Delay(thunk) => thunk()
      case FlatMap(iox, f) =>
        iox match {
          case Pure(x) => runIO(f(x))
          case Delay(thunk) => runIO(f(thunk()))
          case FlatMap(ioy, g) => runIO(ioy.flatMap(x => g(x).flatMap(f)))
        }
    }
}
