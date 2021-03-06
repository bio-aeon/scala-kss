package net.iponweb.intro

object err {
  final case class DivisionError(cause: String)

  def div(x: Int, y: Int): Either[DivisionError, Int] =
    if (y == 0) {
      Left(DivisionError("Impossible to divide by 0"))
    } else {
      Right(x / y)
    }

  for {
    r1 <- div(10, 5).map(_ * 2)
    r2 <- div(r1, 0).orElse(div(15, 3)).left.map(err => DivisionError(s"${err.cause}!!!"))
    r3 <- div(r2, 2)
  } yield r3
}
