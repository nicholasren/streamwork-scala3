package ren.nicholas.streamwork.core.executor

import ren.nicholas.streamwork.core.executor.strategy.RoundRobin

import java.util.concurrent.ConcurrentLinkedQueue

class Dispatcher[In](val incoming: ConcurrentLinkedQueue[In],
                     val parallelism: Int,
                     val strategyOpt: Option[In => Int] = None):
  private val outgoings: IndexedSeq[ConcurrentLinkedQueue[In]] =
    (0 to parallelism).map(_ => ConcurrentLinkedQueue[In]())

  private val strategy: In => Int = strategyOpt match
    case Some(s) => s
    case None => RoundRobin(parallelism)

  def runOnce(): Unit =
    val value = incoming.poll()
    if value != null then
      val index = strategy.apply(value)
      outgoingAt(index).offer(value)


  def outgoingAt(index: Int): ConcurrentLinkedQueue[In] = outgoings(index)


