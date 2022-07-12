package ren.nicholas.streamwork.core.executor

import java.util.concurrent.ConcurrentLinkedQueue
import scala.collection.mutable

class OperatorExecutor[T, R](val incoming: ConcurrentLinkedQueue[T], val operator: T => R) extends Executor[T, R] :
  val outgoing: ConcurrentLinkedQueue[R] = ConcurrentLinkedQueue[R]()
  override val incomingOpt: Option[ConcurrentLinkedQueue[T]] = Some(incoming)
  override val outgoingOpt: Option[ConcurrentLinkedQueue[R]] = Some(outgoing)

  def runOnce(in: Option[T]): Unit = in match
    case Some(value) =>
      val result = operator.apply(value)
      outgoing.offer(result)
    case None => ()

