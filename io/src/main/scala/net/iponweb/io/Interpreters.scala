package net.iponweb.io

import scala.annotation.tailrec
import scala.concurrent.{ExecutionContext, Future}

object Interpreters {

  @tailrec
  def runIOSync[A](io: IO[A]): A =
    io match {
      case Pure(x) => x
      case Delay(thunk) => thunk()
      case FlatMap(iox, f) =>
        iox match {
          case Pure(x) => runIOSync(f(x))
          case Delay(thunk) => runIOSync(f(thunk()))
          case FlatMap(ioy, g) => runIOSync(ioy.flatMap(y => g(y).flatMap(f)))
        }
    }

  def runIOAsync[A](io: IO[A])(implicit ec: ExecutionContext): Future[A] =
    unwrapIO(io) match {
      case Pure(x) => Future.successful(x)
      case Delay(thunk) => Future(thunk())
      case FlatMap(iox, f) =>
        iox match {
          case Delay(thunk) => Future(thunk()).flatMap(x => runIOAsync(f(x)))
          case _ => throw new Exception("Impossible")
        }
    }

  @tailrec
  private def unwrapIO[A](io: IO[A]): IO[A] = io match {
    case FlatMap(FlatMap(iox, f), g) => unwrapIO(iox.flatMap(x => f(x).flatMap(g)))
    case FlatMap(Pure(x), f) => unwrapIO(f(x))
    case _ => io
  }
}
