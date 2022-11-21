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

class MemSink[T] extends Sink[T] :
  var latch: Option[CountDownLatch] = None

  def blockingForN(n: Int): Unit =
    latch = Some(CountDownLatch(n))
    println(s" ${Thread.currentThread().getName} - waiting for $n")
    latch.get.await()
    println(s" ${Thread.currentThread().getName} - passed latch count=${latch.get.getCount}")

  private val xs: ConcurrentLinkedQueue[T] = ConcurrentLinkedQueue()

  override def all: List[T] = xs.asScala.toList

  override def push(t: T): Unit =
    xs.offer(t)
    if latch.isDefined then
      println(s" ${Thread.currentThread().getName} - count: ${latch.get.getCount}")
      latch.get.countDown()

object Sink {
  def console[T](): ConsoleSink[T] = new ConsoleSink[T]

  def memory[T](): MemSink[T] = new MemSink[T]
}
