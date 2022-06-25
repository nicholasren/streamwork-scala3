package ren.nicholas.streamwork.core

import scala.collection.mutable

class OperatorExecutor[T, R](val operator: T => R) {
  val incoming: mutable.Queue[T] = mutable.Queue.empty
  val outgoing: mutable.Queue[R] = mutable.Queue.empty

  def runOnce() = {
    if (incoming.nonEmpty) {
      val result = operator.apply(incoming.dequeue())
      outgoing.enqueue(result)
    }
  }
}
