package ren.nicholas.streamwork.core

import scala.collection.mutable

trait Source[T] extends KStream[T] {
  def get: T
}

object Source {
  def of[T](ts: T*): Source[T] = new Source[T] :
    private val xs: mutable.Queue[T] = mutable.Queue.from(ts)

    override def get: T = xs.dequeue()
}