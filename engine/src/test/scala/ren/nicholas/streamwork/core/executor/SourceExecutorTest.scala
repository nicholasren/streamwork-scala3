package ren.nicholas.streamwork.core.executor

import org.scalatest.BeforeAndAfter
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should
import ren.nicholas.streamwork.core.executor.SourceExecutor
import ren.nicholas.streamwork.core.stream.Source

import scala.collection.mutable.ListBuffer

class SourceExecutorTest extends AnyFunSpec with BeforeAndAfter with should.Matchers {
  describe("A SourceExecutor") {
    var source: Source[String] = Source.of("one", "two")
    var sourceExecutor: SourceExecutor[String] = new SourceExecutor(source)


    describe("runOne") {
      it("should push event to outgoing queue") {
        sourceExecutor.runOnce(None)
        sourceExecutor.outgoing should contain only "one"
      }
    }
  }
}
