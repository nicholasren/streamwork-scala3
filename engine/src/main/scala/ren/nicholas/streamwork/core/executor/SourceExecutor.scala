package ren.nicholas.streamwork.core.executor

import ren.nicholas.streamwork.core.executor.Executor
import ren.nicholas.streamwork.core.stream.Source
import java.util.concurrent.ConcurrentLinkedQueue
import scala.collection.mutable


class SourceExecutor[R](val source: Source[R]) extends Executor[Unit, R] {
  val outgoing: Option[ConcurrentLinkedQueue[R]] = Some(ConcurrentLinkedQueue[R]())
  val incoming: Option[ConcurrentLinkedQueue[Unit]] = None

  /**
   * Run process once.
   *
   * @return true if the thread should continue; false if the thread should exist.
   */
  def runOnce(): Unit = {
    outgoing.get.offer(source.get)
  }
}
