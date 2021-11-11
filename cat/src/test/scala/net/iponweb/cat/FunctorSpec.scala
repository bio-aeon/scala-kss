package net.iponweb.cat

import net.iponweb.cat.FunctorLaws._
import net.iponweb.cat.instances._
import org.specs2.mutable.Specification

class FunctorSpec extends Specification {
  def f(i: Int): Int = i + 2

  def g(i: Int): Int = i * 3

  "Functor instance should" >> {

    "obey laws for List" >> {
      val l: List[Int] = List(5, 10)

      preservesIdentity(l) must beTrue
      preservesComposition(l, f, g) must beTrue
    }

    "obey laws for Option" >> {
      val o: Option[Int] = Some(11)

      preservesIdentity(o) must beTrue
      preservesComposition(o, f, g) must beTrue
    }

    "obey laws for Either" >> {
      val e: Either[String, Int] = Right(19)

      preservesIdentity(e) must beTrue
      preservesComposition(e, f, g) must beTrue
    }
  }
}
