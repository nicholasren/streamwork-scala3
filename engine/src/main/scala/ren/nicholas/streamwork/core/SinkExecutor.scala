package ren.nicholas.streamwork.core

import scala.collection.mutable

class SinkExecutor[T](val incoming: Option[mutable.Queue[T]], sink: Sink[T]) extends Executor[T, Unit] {
  override def outgoing: Option[mutable.Queue[Unit]] = None

  def runOnce(): Unit = {
    if (incoming.nonEmpty)
      val t = incoming.get.dequeue()
      sink.push(t)
  }
}
