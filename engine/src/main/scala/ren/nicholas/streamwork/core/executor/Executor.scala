package ren.nicholas.streamwork.core.executor

import scala.collection.mutable

trait Executor[In, Out] {

  def incoming: Option[mutable.Queue[In]]

  def outgoing: Option[mutable.Queue[Out]]

  def runOnce(): Unit

  def run(): Unit = while true do runOnce()
}
