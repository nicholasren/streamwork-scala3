package ren.nicholas.streamwork.core

import scala.collection.mutable

class SourceExecutor[R](val source: Source[R]) extends Executor[Unit, R] {
  val outgoing: Option[mutable.Queue[R]] = Some(mutable.Queue.empty[R])
  val incoming: Option[mutable.Queue[Unit]] = None

  /**
   * Run process once.
   *
   * @return true if the thread should continue; false if the thread should exist.
   */
  def runOnce(): Unit = {
    outgoing.get.enqueue(source.get)
  }
}
