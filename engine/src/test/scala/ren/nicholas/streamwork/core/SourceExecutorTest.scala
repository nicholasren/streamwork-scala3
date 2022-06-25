package ren.nicholas.streamwork.core

import org.scalatest.funspec.AnyFunSpec
import scala.collection.mutable.ListBuffer
import org.scalatest.matchers.should.Matchers.*
import org.scalatest._


class SourceExecutorTest extends AnyFunSpec with BeforeAndAfter {
  describe("A SourceExecutor") {
    var source: FakeSource = null
    var sourceExecutor: SourceExecutor[String] = null
    before {
      source = new FakeSource()
      sourceExecutor = new SourceExecutor(source)
    }

    describe("runOne") {
      it("should push event to outgoing queue") {
        val events = List("one", "two")
        source.add(events)
        sourceExecutor.runOnce()
        assert(sourceExecutor.outgoing.toList == events)
      }
    }

  }
}

class FakeSource() extends Source[String] {
  private val source: ListBuffer[String] = ListBuffer()

  override def get: List[String] = source.toList

  def add(events: List[String]): Unit = {
    source.addAll(events)
  }
}