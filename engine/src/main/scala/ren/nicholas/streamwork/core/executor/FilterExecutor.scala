package ren.nicholas.streamwork.core.executor

import java.util.concurrent.ConcurrentLinkedQueue

class FilterExecutor[T](
                         val incoming: ConcurrentLinkedQueue[T], val predicate: T => Boolean
                       ) extends Executor[T, T] {
  val outgoing: ConcurrentLinkedQueue[T] = ConcurrentLinkedQueue[T]()
  override val incomingOpt: Option[ConcurrentLinkedQueue[T]] = Some(incoming)
  override val outgoingOpt: Option[ConcurrentLinkedQueue[T]] = Some(outgoing)

  def runOnce(): Unit =
    if !incoming.isEmpty then
      val element = incoming.poll()
      if predicate(element) then
        outgoing.offer(element)
    end if
}
