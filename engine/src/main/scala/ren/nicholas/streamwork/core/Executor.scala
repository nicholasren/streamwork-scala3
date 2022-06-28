package ren.nicholas.streamwork.core

import scala.collection.mutable

trait Executor[In, Out] {

  def incoming: Option[mutable.Queue[In]]

  def outgoing: Option[mutable.Queue[Out]]
}
