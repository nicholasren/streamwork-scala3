package ren.nicholas.streamwork.core.topology

import ren.nicholas.streamwork.core.executor.Executor

import java.util.concurrent.*
import java.util.concurrent.Executors.newFixedThreadPool
import scala.concurrent.ExecutionContext.fromExecutor
import scala.concurrent.{ExecutionContext, Future, blocking}

class Topology(nodes: List[Node]):
  private val executor = newFixedThreadPool(200)
  given ec: ExecutionContext = fromExecutor(executor)

  def executorOf(name: String): Option[AnyExecutor] =
    nodes.find(_.name == name).map(_.executors.head)

  def run(): Unit = {
    nodes
      .flatMap(_.executors)
      .map { executor =>
        Future {
          executor.run()
        }
      }
  }

  def stop(): Unit =executor.shutdown()

  def string(): String = nodes.map(node =>
    s"""${node.name}
       |  ${node.executors.map(_.show()).mkString(", ")}""".stripMargin
  ).mkString(System.lineSeparator())

