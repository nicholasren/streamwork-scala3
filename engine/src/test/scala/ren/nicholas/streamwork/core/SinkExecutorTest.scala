package ren.nicholas.streamwork.core

import org.scalatest.*
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should
import ren.nicholas.streamwork.core.executor.SinkExecutor
import ren.nicholas.streamwork.core.stream.InMemorySink

import java.util.concurrent.ConcurrentLinkedQueue
import scala.collection.mutable
import scala.jdk.CollectionConverters.*


class SinkExecutorTest extends AnyFunSpec with should.Matchers {

  val incoming: ConcurrentLinkedQueue[String] = ConcurrentLinkedQueue[String]()
  val sink: InMemorySink[String] = InMemorySink[String]()
  val executor: SinkExecutor[String] = SinkExecutor(incoming, sink)

  describe("runOnce") {
    it("no operation when incoming queue is empty") {
      executor.runOnce()
      sink.all shouldBe List()
    }

    it("poll record from incoming queue and push to sink") {
      incoming.addAll(List("one", "two", "three").asJava)

      executor.runOnce()
      sink.all shouldBe List("one")

      executor.runOnce()
      sink.all shouldBe List("one", "two")

      executor.runOnce()
      sink.all shouldBe List("one", "two", "three")
    }
  }
}
