package ren.nicholas.streamwork.core

import scala.collection.mutable

trait Source[T] {
  def get: List[T]
}

class SourceExecutor[T](source: Source[T]) {
  val outgoing: mutable.Queue[T] = mutable.Queue.empty[T]

  /**
   * Run process once.
   *
   * @return true if the thread should continue; false if the thread should exist.
   */
  def runOnce(): Unit = {
    val events = source.get
    if (events.nonEmpty)
      outgoing.enqueueAll(events)
  }
}
