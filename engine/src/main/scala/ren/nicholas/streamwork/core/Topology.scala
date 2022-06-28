package ren.nicholas.streamwork.core

import scala.collection.mutable.ListBuffer
import ren.nicholas.streamwork.core.KStream

import java.util.concurrent.Executors
import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

class Topology(nodes: List[Node]) {

  def executorOf(name: String): Option[Executor[? <: Any, ? <: Any]] = {
    nodes.find(_.name == name).map(_.executor)
  }

  def run(): Future[Unit] = {
    val value: List[Future[Unit]] = nodes.map(_.executor).map(executor => {
      Future {
        while (true) {
          executor.runOnce()
        }
      }
    })

    Future {
      value.map(_.value).head
    }
  }
}