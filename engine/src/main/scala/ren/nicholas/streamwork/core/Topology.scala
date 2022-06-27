package ren.nicholas.streamwork.core

class Topology[T, R] {

}

object Topology {
  //TODO: stream builder
  def source[T](name: String, source: Source[T]): Stream[T] = ???

  def run[T](stream: Stream[T]) = ???
}
