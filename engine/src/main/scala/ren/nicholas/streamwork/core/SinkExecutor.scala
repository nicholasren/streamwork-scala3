package ren.nicholas.streamwork.core

import scala.collection.mutable

class SinkExecutor[T](incoming: mutable.Queue[T], sink: Sink[T]) {

  def runOnce(): Unit = {
    if (incoming.nonEmpty)
      val t = incoming.dequeue()
      sink.push(t)
  }
}
