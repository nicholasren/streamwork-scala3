package ren.nicholas.streamwork.core.executor

import scala.collection.mutable

class OperatorExecutor[T, R](val incoming: mutable.Queue[T],
                             val outgoing: mutable.Queue[R],
                             val operator: T => R) {
  def runOnce(): Unit = {
    if (incoming.nonEmpty) {
      val result = operator.apply(incoming.dequeue())
      outgoing.enqueue(result)
    }
  }
}
