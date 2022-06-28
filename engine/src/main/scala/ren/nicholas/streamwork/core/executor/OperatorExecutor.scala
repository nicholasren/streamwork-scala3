package ren.nicholas.streamwork.core.executor

import java.util.concurrent.ConcurrentLinkedQueue
import scala.collection.mutable

class OperatorExecutor[T, R](val incoming: Option[ConcurrentLinkedQueue[T]],
                             val operator: T => R) extends Executor[T, R] {
  var outgoing: Option[ConcurrentLinkedQueue[R]] = Some(ConcurrentLinkedQueue[R]())

  def runOnce(): Unit = {
    if (!incoming.get.isEmpty) {
      val result = operator.apply(incoming.get.poll())
      outgoing.get.offer(result)
    }
  }
}
