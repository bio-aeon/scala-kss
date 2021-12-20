package net.iponweb.monad

package object instances {
  object state extends StateInstances
  object validated extends ValidatedInstances
  object try_ extends TryInstances
  object list extends ListInstances
}
