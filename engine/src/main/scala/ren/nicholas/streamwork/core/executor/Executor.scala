package ren.nicholas.streamwork.core.executor

import java.util.concurrent.ConcurrentLinkedQueue
import scala.collection.mutable

trait Executor[In, Out]:

  def incomingOpt: Option[ConcurrentLinkedQueue[In]]

  def outgoingOpt: Option[ConcurrentLinkedQueue[Out]]

  def runOnce(): Unit

  def run(): Unit = while true do runOnce()
