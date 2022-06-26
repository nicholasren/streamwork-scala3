package ren.nicholas.streamwork.core

class Topology[T, R] {

}

object Topology {
  def source[T](name: String, s: Source[T]): Stream[T] = new SourceExecutor(s)

  def run[T](stream: Stream[T]) = ???
}
