package net.iponweb.monad.instances

import net.iponweb.monad.Semigroup

trait ListInstances {
  implicit def semigroupForList[A]: Semigroup[List[A]] = new Semigroup[List[A]] {
    def combine(x: List[A], y: List[A]): List[A] = x ::: y
  }
}
