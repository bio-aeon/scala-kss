package net.iponweb.monad

import org.specs2.mutable.Specification
import net.iponweb.monad.laws.MonadLaws._
import net.iponweb.monad.instances.state._

class MonadSpec extends Specification {
  def f(i: Int): Int = i + 2

  def g(i: Int): Int = i * 3

  "Monad instance should" >> {

    "obey laws for State" >> {
      preservesLeftIdentity(
        1,
        (x: Int) =>
          State[Int, Int](s => {
            (s + 1, x + 2)
          })
      ) must beTrue
    }
  }
}
