package ren.nicholas.streamwork.core

import scala.collection.mutable

class KStream[T](builder: StreamBuilder, executor: Executor[? <: Any, T]) {
  val outgoing: Option[mutable.Queue[T]] = executor.outgoing

  def to(name: String, sink: Sink[T]): Unit = {
    this.builder.add(name, SinkExecutor(this.outgoing, sink))
  }

}
