package ren.nicholas.streamwork.core.stream

import ren.nicholas.streamwork.core.executor.{Executor, SourceExecutor}
import ren.nicholas.streamwork.core.stream.{KStream, Source}
import ren.nicholas.streamwork.core.Topology


case class Node(name: String, executor: Executor[? <: Any, ? <: Any])

class StreamBuilder() {
  var nodes: List[Node] = List()

  def source[Out](name: String, source: Source[Out]): KStream[Out] = {
    val executor = SourceExecutor(source)
    val stream: KStream[Out] = KStream[Out](this, executor)
    this.add(name, executor)
    stream
  }

  def add[Out](name: String, executor: Executor[? <: Any, Out]): Unit = {
    nodes = Node(name, executor) :: nodes
  }

  def build(): Topology = Topology(nodes)
}