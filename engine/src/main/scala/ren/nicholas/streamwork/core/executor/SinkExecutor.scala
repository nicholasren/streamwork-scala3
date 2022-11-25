package ren.nicholas.streamwork.core.executor

import ren.nicholas.streamwork.core.executor.Executor
import ren.nicholas.streamwork.core.stream.Sink

import java.util.concurrent.BlockingQueue
import scala.collection.mutable

class SinkExecutor[T](val incoming: BlockingQueue[T], sink: Sink[T]) extends Executor[T, Unit] :
  override val incomingOpt: Option[BlockingQueue[T]] = Some(incoming)
  override val outgoingOpt: Option[BlockingQueue[Unit]] = None

  def runOnce(in: Option[T]): Unit = in match
    case Some(t) =>
      sink.push(t)
    case _ => ()