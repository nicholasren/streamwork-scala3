package ren.nicholas.streamwork.core.executor

import ren.nicholas.streamwork.core.executor.Executor
import ren.nicholas.streamwork.core.stream.Sink
import java.util.concurrent.ConcurrentLinkedQueue
import scala.collection.mutable

class SinkExecutor[T](val incoming: Option[ConcurrentLinkedQueue[T]], sink: Sink[T]) extends Executor[T, Unit] {
  override def outgoing: Option[ConcurrentLinkedQueue[Unit]] = None

  def runOnce(): Unit = {
    val queue: ConcurrentLinkedQueue[T] = incoming.get

    if (!queue.isEmpty)
      val t = queue.poll()
      sink.push(t)
  }
}
