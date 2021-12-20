package net.iponweb.monad

import net.iponweb.monad.instances.state._
import net.iponweb.monad.instances.try_._
import net.iponweb.monad.laws.MonadLaws._
import org.specs2.mutable.Specification

import scala.util.Try

class MonadSpec extends Specification {

  "Monad instance should" >> {

    "obey laws for State" >> {
      def f(i: Int): State[Int, Int] = State(s => (s + 2, i + 2))

      def g(i: Int): State[Int, Int] = State(s => (s + 3, i * 3))

      def compare(x: State[Int, Int], y: State[Int, Int]): Boolean = {
        val (s1, r1) = x.run(100)
        val (s2, r2) = y.run(100)
        s1 == s2 && r1 == r2
      }

      val s = State((s: Int) => {
        (s + 1, 5)
      })

      preservesLeftIdentity(5, f)(compare) must beTrue
      preservesRightIdentity(s)(compare)
      preservesAssociativity(s, f, g)(compare)
    }

    "violate the left identity for Try" >> {
      def h(i: Int): Try[Int] = throw new Exception("error")

      monadForTry.flatMap(monadForTry.pure(5))(h) must beFailedTry
      h(5) must throwA[Exception]
    }
  }
}
