package ren.nicholas.streamwork.core.executor.strategy

class RoundRobin[In](parallelism: Int) extends (In => Int) :
  private var index: Int = 0

  def apply(in: In): Int = {
    index = (index + 1) % parallelism
    index
  }
