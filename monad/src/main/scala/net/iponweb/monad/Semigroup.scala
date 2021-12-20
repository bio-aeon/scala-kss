package net.iponweb.monad

trait Semigroup[A] {
  def combine(x: A, y: A): A
}
