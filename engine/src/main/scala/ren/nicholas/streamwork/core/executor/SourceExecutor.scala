package ren.nicholas.streamwork.core.executor

import ren.nicholas.streamwork.core.executor.Executor
import ren.nicholas.streamwork.core.stream.Source

import java.util.concurrent.{BlockingQueue, ConcurrentLinkedQueue, LinkedBlockingQueue}
import scala.collection.mutable


class SourceExecutor[R](val source: Source[R]) extends Executor[Unit, R] :
  val outgoingOpt: Option[BlockingQueue[R]] = Some(LinkedBlockingQueue[R](10))
  val incomingOpt: Option[BlockingQueue[Unit]] = None
  val outgoing: BlockingQueue[R] = outgoingOpt.get

  /**
   * Run process once.
   *
   * @return true if the thread should continue; false if the thread should exist.
   */
  def runOnce(in: Option[Unit]): Unit = {
    val value = source.get
    logger.info(s"generated: $value")
    outgoingOpt.get.offer(value)
  }

