package ren.nicholas.streamwork.core

import scala.collection.mutable

trait Source {
  def get: List[String]
}

class SourceExecutor(source: Source) {
  val outgoingQueue: mutable.Queue[String] = mutable.Queue.empty[String]

  /**
   * Run process once.
   *
   * @return true if the thread should continue; false if the thread should exist.
   */
  def runOnce(): Boolean = {
    val events = source.get
    if (events.isEmpty)
      false
    else
      outgoingQueue.enqueueAll(events)
      true
  }
}
