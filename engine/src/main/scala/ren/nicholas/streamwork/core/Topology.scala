package ren.nicholas.streamwork.core

import scala.collection.mutable.ListBuffer
import ren.nicholas.streamwork.core.KStream

class Topology(nodes: List[Node]) {

  def executorOf(name: String): Option[Executor[? <: Any, ? <: Any]] = {
    nodes.find(_.name == name).map(_.executor)
  }
}