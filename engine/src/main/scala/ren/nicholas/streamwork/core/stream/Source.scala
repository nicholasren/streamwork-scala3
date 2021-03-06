package ren.nicholas.streamwork.core.stream

import ren.nicholas.streamwork.core.stream.Source

import scala.collection.mutable

trait Source[T]:
  def get: T


object Source {
  def continually[T](elem: => T): Source[T] = new Source[T] :
    override def get: T = elem

  def of[T](ts: T*): Source[T] = new Source[T] :
    private val xs: mutable.Queue[T] = mutable.Queue.from(ts)

    override def get: T = xs.dequeue()
}