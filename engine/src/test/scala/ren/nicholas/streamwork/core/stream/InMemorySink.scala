package ren.nicholas.streamwork.core.stream

import ren.nicholas.streamwork.core.stream.Sink

import scala.collection.mutable

class InMemorySink[T] extends Sink[T] :
  private val mem: mutable.Queue[T] = mutable.Queue.empty

  def all: List[T] = mem.toList

  def push(t: T): Unit = mem.enqueue(t)

