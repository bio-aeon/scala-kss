package net.iponweb.monad

import net.iponweb.monad.Validated.{Invalid, Valid}
import net.iponweb.monad.instances.list._
import net.iponweb.monad.instances.validated._
import net.iponweb.monad.laws.ApplicativeLaws._
import org.specs2.mutable.Specification

class ApplicativeSpec extends Specification {

  "Applicative instance should" >> {

    "obey laws for Validated" >> {
      val fv: Validated[List[String], Int => Int] = Validated.valid(_ + 2)

      val gv: Validated[List[String], Int => Int] = Validated.valid(_ * 3)

      def compare(x: Validated[List[String], Int], y: Validated[List[String], Int]): Boolean =
        (x, y) match {
          case (Valid(a), Valid(b)) => a == b
          case (Invalid(e1), Invalid(e2)) => e1 == e2
          case _ => false
        }

      val v = Validated.valid[List[String], Int](5)

      preservesIdentity(v)(compare)
      preservesComposition(v, fv, gv)(compare)
      preservesHomomorphism(5, (_: Int) + 11)(compare)
      preservesInterchange(5, fv)(compare)
    }
  }
}
