package ren.nicholas.streamwork.core.topology

import ren.nicholas.streamwork.core.executor.Executor
import ren.nicholas.streamwork.core.stream.KStream

import java.util.concurrent.Executors
import java.util.concurrent.Executors.newFixedThreadPool
import scala.concurrent.ExecutionContext.fromExecutor
import scala.concurrent.{ExecutionContext, Future}

class Topology(nodes: List[Node]):
  given executionContext: ExecutionContext = fromExecutor(newFixedThreadPool(4))

  def executorOf(name: String): Option[Executor[? <: Any, ? <: Any]] =
    nodes.find(_.name == name).map(_.executor)

  def run(): Future[Unit] =
    val value: List[Future[Unit]] = nodes.reverse.map(_.executor).map(executor => Future(executor.run()))
    Future(value.map(_.value).head)

