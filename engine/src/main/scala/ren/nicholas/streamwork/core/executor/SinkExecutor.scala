package ren.nicholas.streamwork.core.executor

import ren.nicholas.streamwork.core.executor.Executor
import ren.nicholas.streamwork.core.stream.Sink

import java.util.concurrent.ConcurrentLinkedQueue
import scala.collection.mutable

class SinkExecutor[T](val incoming: ConcurrentLinkedQueue[T], sink: Sink[T]) extends Executor[T, Unit] {
  override val incomingOpt: Option[ConcurrentLinkedQueue[T]] = Some(incoming)
  override val outgoingOpt: Option[ConcurrentLinkedQueue[Unit]] = None

  def runOnce(): Unit = {
    if (!incoming.isEmpty)
      val t = incoming.poll()
      sink.push(t)
  }
}
