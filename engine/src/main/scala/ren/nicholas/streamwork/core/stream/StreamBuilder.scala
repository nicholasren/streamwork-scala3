package ren.nicholas.streamwork.core.stream

import ren.nicholas.streamwork.core.executor.{Executor, SinkExecutor, SourceExecutor}
import ren.nicholas.streamwork.core.stream.{KStream, Source}
import ren.nicholas.streamwork.core.topology
import ren.nicholas.streamwork.core.topology.{Node, Topology}

import java.util.concurrent.ConcurrentLinkedQueue


class StreamBuilder():
  var nodes: List[Node] = List()

  def source[Out](name: String, source: Source[Out], parallelism: Int = 1): KStream[Out] =
    val executors = (0 until parallelism).map(_ => SourceExecutor(source))
    this.add(name, executors)

  private[stream]
  def add[Out](name: String, executors: Seq[Executor[_, Out]]): KStream[Out] =
    nodes = topology.Node(name, executors) :: nodes
    KStream(this, executors.map(_.outgoingOpt.get))

  private[stream]
  def sink[In](name: String, incoming: Seq[ConcurrentLinkedQueue[In]], sink: Sink[In]): Unit =
    val executors = incoming.map(SinkExecutor(_, sink))
    nodes = topology.Node(name, executors) :: nodes

  def build(): Topology = Topology(nodes.reverse)