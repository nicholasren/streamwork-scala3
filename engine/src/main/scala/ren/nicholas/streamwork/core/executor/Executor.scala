package ren.nicholas.streamwork.core.executor

import org.slf4j.{Logger, LoggerFactory}

import java.util.concurrent.ConcurrentLinkedQueue
import scala.collection.mutable

trait Executor[In, Out]:

  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  def incomingOpt: Option[ConcurrentLinkedQueue[In]]

  def outgoingOpt: Option[ConcurrentLinkedQueue[Out]]

  def runOnce(in: Option[In]): Unit

  def run(): Unit = while (true) {
    val maybeIn: Option[In] = incomingOpt.flatMap { queue => Option(queue.poll()) }
    if maybeIn.isDefined then logger.info(s"incoming: ${maybeIn.get}")
    runOnce(maybeIn)
  }

  def show(): String =
    s"${show(incomingOpt)} ==> ${show(outgoingOpt)}"

  private def show(queue: Option[ConcurrentLinkedQueue[?]]): String = queue match
    case Some(q) => s"${q.hashCode()}"
    case None => "none"