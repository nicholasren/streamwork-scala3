package ren.nicholas.streamwork.core.stream

import ren.nicholas.streamwork.core.executor.*
import ren.nicholas.streamwork.core.stream.{Sink, StreamBuilder}
import ren.nicholas.streamwork.core.topology.Node

import java.util.concurrent.ConcurrentLinkedQueue

class KStream[In](builder: StreamBuilder, executors: Seq[Executor[? <: Any, In]]):
  def map[Out](name: String, f: In => Out): KStream[Out] =
    val nextExecutors = nextExecutorOf((executor: Executor[_, In]) => OperatorExecutor(executor.outgoingOpt.get, f))
    this.builder.add(name, nextExecutors)


  def filter(name: String, p: In => Boolean): KStream[In] =
    val nextExecutors = nextExecutorOf((executor: Executor[_, In]) => FilterExecutor(executor.outgoingOpt.get, p))
    this.builder.add(name, nextExecutors)

  def to(name: String, sink: Sink[In]): Unit =
    val nextExecutors = nextExecutorOf((executor: Executor[_, In]) => SinkExecutor(executor.outgoingOpt.get, sink))
    this.builder.add(name, nextExecutors)

  private def nextExecutorOf[Out](nextOf: Executor[_, In] => Executor[In, Out]): Seq[Executor[In, Out]] = executors.map(executor => nextOf(executor))