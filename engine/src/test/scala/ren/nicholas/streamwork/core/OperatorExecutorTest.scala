package ren.nicholas.streamwork.core

import org.scalatest.BeforeAndAfter
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should
import ren.nicholas.streamwork.core.executor.OperatorExecutor

import java.util.concurrent.ConcurrentLinkedQueue
import scala.collection.mutable

class OperatorExecutorTest extends AnyFunSpec with BeforeAndAfter with should.Matchers {
  describe("A OperatorExecutor") {
    var operatorExecutor: OperatorExecutor[String, Int] = null
    var incoming: ConcurrentLinkedQueue[String] = null

    before {
      incoming = ConcurrentLinkedQueue()
      operatorExecutor = new OperatorExecutor[String, Int](incoming, _.length)
    }

    describe("runOnce") {
      it("should take element and apply operation") {
        incoming.offer("One")

        operatorExecutor.runOnce()

        assert(operatorExecutor.outgoing.peek() == 3)
      }

      it("should no operation if incoming is empty") {
        operatorExecutor.runOnce()

        assert(operatorExecutor.outgoing.isEmpty)
      }
    }
  }
}
