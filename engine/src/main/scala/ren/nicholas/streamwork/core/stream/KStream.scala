package ren.nicholas.streamwork.core.stream

import ren.nicholas.streamwork.core.executor.*
import ren.nicholas.streamwork.core.stream.{Sink, StreamBuilder}

import java.util.concurrent.ConcurrentLinkedQueue

class KStream[In](builder: StreamBuilder, executor: Executor[? <: Any, In]):
  val outgoing: Option[ConcurrentLinkedQueue[In]] = executor.outgoingOpt

  def map[Out](name: String, f: In => Out): KStream[Out] =
    this.builder.add(name, OperatorExecutor(this.outgoing.get, f))

  def filter(name: String, p: In => Boolean): KStream[In] =
    this.builder.add(name, FilterExecutor(this.outgoing.get, p))

  def to(name: String, sink: Sink[In]): Unit =
    this.builder.add(name, SinkExecutor(this.outgoing.get, sink))
