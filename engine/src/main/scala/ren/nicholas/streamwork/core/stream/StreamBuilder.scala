package ren.nicholas.streamwork.core.stream

import ren.nicholas.streamwork.core.executor.{Executor, SourceExecutor}
import ren.nicholas.streamwork.core.stream.{KStream, Source}
import ren.nicholas.streamwork.core.topology
import ren.nicholas.streamwork.core.topology.{Node, Topology}


class StreamBuilder() {
  var nodes: List[Node] = List()

  def source[Out](name: String, source: Source[Out]): KStream[Out] = {
    this.add(name, SourceExecutor(source))
  }

  private[stream]
  def add[Out](name: String, executor: Executor[? <: Any, Out]): KStream[Out] = {
    val stream: KStream[Out] = KStream[Out](this, executor)
    nodes = topology.Node(name, executor) :: nodes
    stream
  }

  def build(): Topology = Topology(nodes)
}
