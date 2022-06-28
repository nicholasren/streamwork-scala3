package ren.nicholas.streamwork.core

import scala.collection.mutable.ListBuffer
import ren.nicholas.streamwork.core.KStream

class Topology {

}

class StreamBuilder {
  def source[T](name: String, source: Source[T]): KStream[T] = {
    val stream: KStream[T] = KStream[T]()
    stream
  }

  def build(): Topology = ???
}