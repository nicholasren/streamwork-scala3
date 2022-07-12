package ren.nicholas.streamwork.core.executor

import java.util.concurrent.ConcurrentLinkedQueue
import scala.collection.mutable

trait Executor[In, Out]:

  def incomingOpt: Option[ConcurrentLinkedQueue[In]]

  def outgoingOpt: Option[ConcurrentLinkedQueue[Out]]

  def runOnce(): Unit

  def run(): Unit = while true do runOnce()

  def show(): String =
    s"${show(incomingOpt)} ==> ${show(outgoingOpt)}"

  private def show(queue: Option[ConcurrentLinkedQueue[?]]): String = queue match
    case Some(q) => s"${q.hashCode()}"
    case None => "none"