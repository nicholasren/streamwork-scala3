package ren.nicholas.streamwork.core.stream

import ren.nicholas.streamwork.core.executor.{Executor, OperatorExecutor, SinkExecutor}
import ren.nicholas.streamwork.core.stream.Sink
import ren.nicholas.streamwork.core.stream.StreamBuilder

import java.util.concurrent.ConcurrentLinkedQueue

class KStream[T](builder: StreamBuilder, executor: Executor[? <: Any, T]) {
  val outgoing: Option[ConcurrentLinkedQueue[T]] = executor.outgoing

  def map[Out](name: String, f: T => Out): KStream[Out] = {
    val executor = OperatorExecutor(this.outgoing, f)
    val stream: KStream[Out] = KStream[Out](builder, executor)
    builder.add(name, executor)
    stream
  }

  def to(name: String, sink: Sink[T]): Unit = {
    this.builder.add(name, SinkExecutor(this.outgoing, sink))
  }

}
