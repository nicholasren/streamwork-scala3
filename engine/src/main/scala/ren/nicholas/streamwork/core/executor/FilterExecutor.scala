package ren.nicholas.streamwork.core.executor

import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}

class FilterExecutor[T](
                         val incoming: BlockingQueue[T],
                         val predicate: T => Boolean
                       ) extends Executor[T, T] {
  val outgoing: BlockingQueue[T] = LinkedBlockingQueue[T](10)
  override val incomingOpt: Option[BlockingQueue[T]] = Some(incoming)
  override val outgoingOpt: Option[BlockingQueue[T]] = Some(outgoing)

  def runOnce(in: Option[T]): Unit = in match
    case Some(value) if predicate(value) =>
      logger.info(s"outgoing: $value")
      outgoing.offer(value)
    case Some(value) if !predicate(value) =>
      logger.info(s"dropping: $value")
    case _ => ()
}
