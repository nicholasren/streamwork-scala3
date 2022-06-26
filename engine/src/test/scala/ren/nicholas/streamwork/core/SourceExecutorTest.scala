package ren.nicholas.streamwork.core

import scala.collection.mutable.ListBuffer

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers.*
import org.scalatest._


class SourceExecutorTest extends AnyFunSpec with BeforeAndAfter {
  describe("A SourceExecutor") {
    var source: Source[String] = Source.of("one", "two")
    var sourceExecutor: SourceExecutor[String] = new SourceExecutor(source)


    describe("runOne") {
      it("should push event to outgoing queue") {
        sourceExecutor.runOnce()
        sourceExecutor.outgoing should contain only ("one")
      }
    }
  }
}
