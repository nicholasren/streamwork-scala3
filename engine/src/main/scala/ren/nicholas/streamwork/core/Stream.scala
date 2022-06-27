package ren.nicholas.streamwork.core

class Stream[T] {
  def map[T, R](name: String, f: T => R): Stream[R] = ???

  def to(name: String, sink: Sink[T]): Stream[T] = ???
}
