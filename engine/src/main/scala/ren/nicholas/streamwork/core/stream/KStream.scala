package ren.nicholas.streamwork.core.stream

import ren.nicholas.streamwork.core.executor.{Executor, OperatorExecutor, SinkExecutor, SourceExecutor}
import ren.nicholas.streamwork.core.stream.{Sink, StreamBuilder}

import java.util.concurrent.ConcurrentLinkedQueue

class KStream[T](builder: StreamBuilder, executor: Executor[? <: Any, T]):
  val outgoing: Option[ConcurrentLinkedQueue[T]] = executor.outgoingOpt

  def map[Out](name: String, f: T => Out): KStream[Out] =
    this.builder.add(name, OperatorExecutor(this.outgoing.get, f))

  def to(name: String, sink: Sink[T]): Unit =
    this.builder.add(name, SinkExecutor(this.outgoing.get, sink))
