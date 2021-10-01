package net.iponweb.intro

import org.specs2.mutable.Specification

import scala.util.Try

class ExceptionsRTSpec extends Specification {

  def func1(i: Int): Int = {
    val y: Int = throw new Exception("err")

    try {
      val x = i + 5
      x + y
    } catch {
      case e: Exception => 11
    }
  }

  def func2(i: Int): Int =
    try {
      val x = i + 5
      x + ((throw new Exception("err")): Int)
    } catch {
      case _: Exception => 11
    }

  "Exceptions should" >> {
    "be raised in func1" >> {
      func1(10) must throwA[Exception]
    }

    "be caught in func2" >> {
      func2(10) mustEqual(11)
    }

    "break referential transparency" >> {
      val r1 = Try(func1(10))
      val r2 = Try(func2(10))
      r1 mustNotEqual(r2)
    }
  }
}
