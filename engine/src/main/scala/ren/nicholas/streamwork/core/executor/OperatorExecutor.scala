package ren.nicholas.streamwork.core.executor

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import scala.collection.mutable

class OperatorExecutor[T, R](val incoming: BlockingQueue[T], val operator: T => R) extends Executor[T, R] :
  val outgoing: BlockingQueue[R] = LinkedBlockingQueue[R](10)
  override val incomingOpt: Option[BlockingQueue[T]] = Some(incoming)
  override val outgoingOpt: Option[BlockingQueue[R]] = Some(outgoing)

  def runOnce(in: Option[T]): Unit = in match
    case Some(value) =>
      val result = operator.apply(value)
      logger.info(s"outgoing: $result")
      outgoing.offer(result)
    case None => ()

