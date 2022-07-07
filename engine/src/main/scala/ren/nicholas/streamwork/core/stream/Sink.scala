package ren.nicholas.streamwork.core.stream


import scala.collection.mutable

trait Sink[T]:
  def all: List[T]

  def push(t: T): Unit


class ConsoleSink[T] extends Sink[T] :
  override def all: List[T] = List()

  override def push(t: T): Unit = println(t)

object Sink {
  def console[T](): ConsoleSink[T] = new ConsoleSink[T]

  def empty[T](): Sink[T] = new Sink[T] :
    private val xs: mutable.Queue[T] = mutable.Queue.empty

    override def all: List[T] = xs.toList

    override def push(t: T): Unit = xs.enqueue(t)
}
