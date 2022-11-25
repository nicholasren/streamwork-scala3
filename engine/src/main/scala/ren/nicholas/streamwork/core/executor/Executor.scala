package ren.nicholas.streamwork.core.executor

import org.slf4j.{Logger, LoggerFactory}

import java.util.concurrent.{BlockingQueue, ConcurrentLinkedQueue}
import scala.collection.mutable

trait Executor[In, Out]:

  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  def incomingOpt: Option[BlockingQueue[In]]

  def outgoingOpt: Option[BlockingQueue[Out]]

  def runOnce(in: Option[In]): Unit

  def run(): Unit =
    //TODO: ideally, this should only run when there is element in the incoming queue
    while (true) {
      val maybeIn: Option[In] = incomingOpt.flatMap { queue => Option(queue.poll()) }
      if maybeIn.isDefined then logger.info(s"incoming: ${maybeIn.get}")
      runOnce(maybeIn)
    }


  def show(): String =
    s"${show(incomingOpt)} ==> ${show(outgoingOpt)}"

  private def show(queue: Option[BlockingQueue[?]]): String = queue match
    case Some(q) => s"Q:${q.hashCode()}"
    case None => "none"