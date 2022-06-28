package ren.nicholas.streamwork.core.executor

import scala.collection.mutable

class OperatorExecutor[T, R](val incoming: Option[mutable.Queue[T]],
                             val operator: T => R) extends Executor[T, R] {
  var outgoing: Option[mutable.Queue[R]] = Some(mutable.Queue.empty)

  def runOnce(): Unit = {
    if (incoming.nonEmpty) {
      val result = operator.apply(incoming.get.dequeue())
      outgoing.get.enqueue(result)
    }
  }
}
