package ren.nicholas.streamwork.core.executor

import ren.nicholas.streamwork.core.executor.Executor
import ren.nicholas.streamwork.core.stream.Source

import java.util.concurrent.ConcurrentLinkedQueue
import scala.collection.mutable


class SourceExecutor[R](val source: Source[R]) extends Executor[Unit, R] {
  val outgoingOpt: Option[ConcurrentLinkedQueue[R]] = Some(ConcurrentLinkedQueue[R]())
  val incomingOpt: Option[ConcurrentLinkedQueue[Unit]] = None
  val outgoing: ConcurrentLinkedQueue[R] = outgoingOpt.get

  /**
   * Run process once.
   *
   * @return true if the thread should continue; false if the thread should exist.
   */
  def runOnce(): Unit = {
    outgoingOpt.get.offer(source.get)
  }
}
