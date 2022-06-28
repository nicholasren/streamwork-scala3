package ren.nicholas.streamwork.core

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should
import org.scalatest.*
import ren.nicholas.streamwork.core.executor.SinkExecutor
import ren.nicholas.streamwork.core.stream.InMemorySink

import scala.collection.mutable

class SinkExecutorTest extends AnyFunSpec with should.Matchers {

  val incoming: mutable.Queue[String] = mutable.Queue.empty
  val sink: InMemorySink[String] = InMemorySink[String]()
  val executor: SinkExecutor[String] = SinkExecutor(Some(incoming), sink)

  describe("runOnce") {
    it("no operation when incoming queue is empty") {
      executor.runOnce()
      sink.all shouldBe List()
    }

    it("poll record from incoming queue and push to sink") {
      incoming.enqueueAll(List("one", "two", "three"))

      executor.runOnce()
      sink.all shouldBe List("one")

      executor.runOnce()
      sink.all shouldBe List("one", "two")

      executor.runOnce()
      sink.all shouldBe List("one", "two", "three")
    }
  }
}
