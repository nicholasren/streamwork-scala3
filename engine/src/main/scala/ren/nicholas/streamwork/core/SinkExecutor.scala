package ren.nicholas.streamwork.core

import scala.collection.mutable

class SinkExecutor[T](val incoming: Option[mutable.Queue[T]], sink: Sink[T]) extends Executor[T, Unit] {
  override def outgoing: Option[mutable.Queue[Unit]] = None

  def runOnce(): Unit = {
    val queue: mutable.Queue[T] = incoming.get

    if (queue.nonEmpty)
      val t = queue.dequeue()
      sink.push(t)
  }
}
