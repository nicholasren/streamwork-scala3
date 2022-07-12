package ren.nicholas.streamwork.core.stream


import java.util.concurrent.ConcurrentLinkedQueue
import scala.collection.mutable
import scala.jdk.CollectionConverters.*

trait Sink[T]:
  def all: List[T]

  def push(t: T): Unit


class ConsoleSink[T] extends Sink[T] :
  override def all: List[T] = List()

  override def push(t: T): Unit = println(t)

object Sink {
  def console[T](): ConsoleSink[T] = new ConsoleSink[T]

  def memory[T](): Sink[T] = new Sink[T] :
    private val xs: ConcurrentLinkedQueue[T] = ConcurrentLinkedQueue()

    override def all: List[T] = xs.asScala.toList

    override def push(t: T): Unit = xs.offer(t)
}
