package ren.nicholas.streamwork.core

import scala.collection.mutable.ListBuffer
import ren.nicholas.streamwork.core.executor.Executor
import ren.nicholas.streamwork.core.stream.{KStream, Node}

import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext.fromExecutor
import scala.concurrent.Future

class Topology(nodes: List[Node]) {

  implicit val ec: scala.concurrent.ExecutionContext = fromExecutor(Executors.newFixedThreadPool(4))

  def executorOf(name: String): Option[Executor[? <: Any, ? <: Any]] = nodes.find(_.name == name).map(_.executor)

  def run(): Future[Unit] = {
    val value: List[Future[Unit]] = nodes.reverse.map(_.executor).map(executor => Future(executor.run()))

    Future {
      value.map(_.value).head
    }
  }
}