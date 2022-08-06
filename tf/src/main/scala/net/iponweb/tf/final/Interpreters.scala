package net.iponweb.tf.`final`

import cats.syntax.applicative._
import cats.syntax.apply._
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.{Applicative, MonadError}

object Interpreters {

  def expressionEval[F[_]: Applicative]: Expression[F, Int] = new Expression[F, Int] {
    override def intLiteral(n: Int): F[Int] = n.pure[F]

    override def negate(a: F[Int]): F[Int] = a.map(-_)

    override def add(a: F[Int], b: F[Int]): F[Int] = (a, b).mapN(_ + _)
  }

  def logicalEval[F[_]: Applicative]: Logical[F, Boolean] = new Logical[F, Boolean] {
    override def boolLiteral(b: Boolean): F[Boolean] = b.pure[F]

    override def or(a: F[Boolean], b: F[Boolean]): F[Boolean] = (a, b).mapN(_ || _)
  }

  def divisionEval[F[_]](implicit F: MonadError[F, String]): Division[F, Int] =
    new Division[F, Int] {
      override def divide(a: F[Int], b: F[Int]): F[Int] =
        b.map(_ == 0).ifM(F.raiseError("Division by zero"), (a, b).mapN(_ / _))
    }

  def expressionShow[F[_]: Applicative]: Expression[F, String] = new Expression[F, String] {
    override def intLiteral(n: Int): F[String] = n.toString.pure[F]

    override def negate(a: F[String]): F[String] = a.map(x => s"-$x")

    override def add(a: F[String], b: F[String]): F[String] = (a, b).mapN((x, y) => s"($x + $y)")
  }

  def divisionShow[F[_]: Applicative]: Division[F, String] = new Division[F, String] {
    override def divide(a: F[String], b: F[String]): F[String] = (a, b).mapN((x, y) => s"($x / $y)")
  }
}
