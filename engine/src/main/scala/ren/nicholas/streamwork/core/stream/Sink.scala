package ren.nicholas.streamwork.core.stream


import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.{ConcurrentLinkedQueue, CountDownLatch}
import scala.collection.mutable
import scala.jdk.CollectionConverters.*

trait Sink[T]:
  def all: List[T]

  def push(t: T): Unit


class ConsoleSink[T] extends Sink[T] :
  override def all: List[T] = List()

  override def push(t: T): Unit = println(t)

class MemSink[T](count: Int) extends Sink[T] :
  var latch: CountDownLatch = CountDownLatch(count)

  def blocking(): Unit =
    latch.await()

  private val xs: ConcurrentLinkedQueue[T] = ConcurrentLinkedQueue()

  override def all: List[T] = xs.asScala.toList

  override def push(t: T): Unit =
    val from = latch.getCount
    xs.offer(t)
    latch.countDown()

object Sink {
  def console[T](): ConsoleSink[T] = new ConsoleSink[T]

  def memory[T](count: Int = 100): MemSink[T] = new MemSink[T](count)
}
