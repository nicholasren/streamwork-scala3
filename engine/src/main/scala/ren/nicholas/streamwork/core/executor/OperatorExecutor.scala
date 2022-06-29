package ren.nicholas.streamwork.core.executor

import java.util.concurrent.ConcurrentLinkedQueue
import scala.collection.mutable

class OperatorExecutor[T, R](val incoming: ConcurrentLinkedQueue[T], val operator: T => R) extends Executor[T, R] {
  val outgoing: ConcurrentLinkedQueue[R] = ConcurrentLinkedQueue[R]()
  override val incomingOpt: Option[ConcurrentLinkedQueue[T]] = Some(incoming)
  override val outgoingOpt: Option[ConcurrentLinkedQueue[R]] = Some(outgoing)

  def runOnce(): Unit = {
    if (!incoming.isEmpty) {
      val result = operator.apply(incoming.poll())
      outgoing.offer(result)
    }
  }
}
