package ren.nicholas.streamwork.core.executor

import org.scalatest.BeforeAndAfter
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should
import ren.nicholas.streamwork.core.executor.{FilterExecutor, OperatorExecutor}

import java.util.concurrent.ConcurrentLinkedQueue
import scala.compiletime.uninitialized
import scala.jdk.CollectionConverters.*

class FilterExecutorTest extends AnyFunSpec with BeforeAndAfter with should.Matchers {
  var executor: FilterExecutor[String] = uninitialized
  var incoming: ConcurrentLinkedQueue[String] = uninitialized

  describe("FilterExecutor") {
    before {
      incoming = ConcurrentLinkedQueue()
      executor = new FilterExecutor[String](incoming, _.length > 3)
    }

    it("should only send to downstream when predicate is met") {
      List("hello", "cat", "world").map(Some.apply).foreach(executor.runOnce)
      executor.outgoing.asScala.toList should contain allOf("hello", "world")
    }
  }
}
