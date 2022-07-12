package ren.nicholas.streamwork.core.stream

import ren.nicholas.streamwork.core.executor.{Executor, SourceExecutor}
import ren.nicholas.streamwork.core.stream.{KStream, Source}
import ren.nicholas.streamwork.core.topology
import ren.nicholas.streamwork.core.topology.{Node, Topology}


class StreamBuilder():
  var nodes: List[Node] = List()

  def source[Out](name: String, source: Source[Out], parallelism: Int = 1): KStream[Out] =
    val executors = (0 to parallelism).map(_ => SourceExecutor(source))
    this.add(name, executors)

  private[stream]
  def add[Out](name: String, executors: Seq[Executor[? <: Any, Out]]): KStream[Out] =
    val node = topology.Node(name, executors)
    nodes = node :: nodes
    KStream[Out](this, executors)


  def build(): Topology = Topology(nodes.reverse)