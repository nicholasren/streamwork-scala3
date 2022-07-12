package ren.nicholas.streamwork.core.stream

import ren.nicholas.streamwork.core.executor.*
import ren.nicholas.streamwork.core.stream.{Sink, StreamBuilder}
import ren.nicholas.streamwork.core.topology.Node

import java.util.concurrent.ConcurrentLinkedQueue

class KStream[In](builder: StreamBuilder, incoming: Seq[ConcurrentLinkedQueue[In]]):
  def map[Out](name: String, f: In => Out): KStream[Out] =
    val next = incoming.map(OperatorExecutor(_, f))
    this.builder.add(name, next)


  def filter(name: String, p: In => Boolean): KStream[In] =
    val next = incoming.map(FilterExecutor(_, p))
    this.builder.add(name, next)

  def to(name: String, sink: Sink[In]): Unit =
    this.builder.sink(name, incoming, sink)