package ren.nicholas.streamwork.core.topology

import ren.nicholas.streamwork.core.executor.Executor

import java.util.concurrent.Executors
import java.util.concurrent.Executors.newFixedThreadPool
import scala.concurrent.ExecutionContext.fromExecutor
import scala.concurrent.{ExecutionContext, Future}

class Topology(nodes: List[Node]):
  given executionContext: ExecutionContext = fromExecutor(newFixedThreadPool(4))

  def executorsOf(name: String): List[Executor[? <: Any, ? <: Any]] = nodes.filter(_.name == name).map(_.executor)

  def executorOf(name: String): Option[Executor[? <: Any, ? <: Any]] =
    nodes.find(_.name == name).map(_.executor)

  def run(): Future[Unit] =
    val value: List[Future[Unit]] = nodes.map(node => Future(node.executor.run()))
    Future(value.map(_.value).head)