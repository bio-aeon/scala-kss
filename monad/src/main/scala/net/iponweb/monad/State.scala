package net.iponweb.monad

final case class State[S, +A](run: S => (S, A)) {
  def flatMap[B](f: A => State[S, B]): State[S, B] =
    State(s => {
      val (s1, a) = run(s)
      f(a).run(s1)
    })
}
