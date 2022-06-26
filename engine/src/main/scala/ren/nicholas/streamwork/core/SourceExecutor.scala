package ren.nicholas.streamwork.core

import scala.collection.mutable

class SourceExecutor[T](source: ren.nicholas.streamwork.core.Source[T]) extends Stream[T] {
  val outgoing: mutable.Queue[T] = mutable.Queue.empty[T]

  /**
   * Run process once.
   *
   * @return true if the thread should continue; false if the thread should exist.
   */
  def runOnce(): Unit = {
    outgoing.enqueue(source.get)
  }
}
