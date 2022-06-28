package ren.nicholas.streamwork.core.executor

import java.util.concurrent.ConcurrentLinkedQueue
import scala.collection.mutable

trait Executor[In, Out] {

  def incoming: Option[ConcurrentLinkedQueue[In]]

  def outgoing: Option[ConcurrentLinkedQueue[Out]]

  def runOnce(): Unit

  def run(): Unit = while true do runOnce()
}
