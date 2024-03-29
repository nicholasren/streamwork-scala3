package ren.nicholas.streamwork.core.executor

import org.scalatest.BeforeAndAfter
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should
import ren.nicholas.streamwork.core.executor.OperatorExecutor

import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}
import scala.collection.mutable
import scala.compiletime.uninitialized

class OperatorExecutorTest extends AnyFunSpec with BeforeAndAfter with should.Matchers {
  var operatorExecutor: OperatorExecutor[String, Int] = uninitialized
  var incoming: BlockingQueue[String] = uninitialized

  describe("A OperatorExecutor") {
    before {
      incoming = LinkedBlockingQueue[String]()
      operatorExecutor = new OperatorExecutor[String, Int](incoming, _.length)
    }

    describe("runOnce") {
      it("should take element and apply operation") {
        operatorExecutor.runOnce(Some("One"))

        assert(operatorExecutor.outgoing.peek() == 3)
      }

      it("should no operation if incoming is empty") {
        operatorExecutor.runOnce(None)
        assert(operatorExecutor.outgoing.isEmpty)
      }
    }
  }
}
