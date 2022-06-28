package ren.nicholas.streamwork.core

import scala.collection.mutable

trait Sink[T] extends KStream[T] {
  def all: List[T]

  def push(t: T): Unit
}

object Sink {
  def empty[T]() = new Sink[T] :
    private val xs: mutable.Queue[T] = mutable.Queue.empty

    override def all: List[T] = xs.toList

    override def push(t: T): Unit = xs.enqueue(t)
}
