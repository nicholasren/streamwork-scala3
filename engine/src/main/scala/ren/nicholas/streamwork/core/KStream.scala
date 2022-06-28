package ren.nicholas.streamwork.core

import scala.collection.mutable

class KStream[T] {

  def to(name: String, sink: Sink[T]): Unit = ???

}
