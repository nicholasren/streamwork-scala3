package ren.nicholas.streamwork.core.topology

import ren.nicholas.streamwork.core.executor.Executor

import java.util.concurrent.Executors.newFixedThreadPool
import java.util.concurrent.{ConcurrentLinkedQueue, Executors}
import scala.concurrent.ExecutionContext.fromExecutor
import scala.concurrent.{ExecutionContext, Future, blocking}

class Topology(nodes: List[Node]):
  given executionContext: ExecutionContext = ExecutionContext.global

  def executorsOf(name: String): List[Executor[? <: Any, ? <: Any]] = nodes.filter(_.name == name).flatMap(_.executors)

  def executorOf(name: String): Option[Executor[? <: Any, ? <: Any]] =
    nodes.find(_.name == name).map(_.executors.head)

  def run(): Unit =
    nodes
      .flatMap(_.executors)
      .map { executor =>
        Future {
          blocking {
            executor.run()
          }
        }
      }

  def string(): String = nodes.map(node =>
    s"""${node.name}
       |  ${node.executors.map(_.show()).mkString(", ")}""".stripMargin
  ).mkString(System.lineSeparator())

