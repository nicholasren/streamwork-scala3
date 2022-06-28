package ren.nicholas.streamwork.core

import org.scalatest.matchers.should
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.BeforeAndAfter
import ren.nicholas.streamwork.core.executor.OperatorExecutor
import java.util.concurrent.ConcurrentLinkedQueue

import scala.collection.mutable

class OperatorExecutorTest extends AnyFunSpec with BeforeAndAfter with should.Matchers {
  describe("A OperatorExecutor") {
    var operatorExecutor: OperatorExecutor[String, Int] = null
    var incoming: Option[ConcurrentLinkedQueue[String]] = null
    var outgoing: Option[ConcurrentLinkedQueue[Int]] = null

    before {
      incoming = Some(ConcurrentLinkedQueue())
      outgoing = Some(ConcurrentLinkedQueue())
      operatorExecutor = new OperatorExecutor[String, Int](incoming, _.length)
      operatorExecutor.outgoing = outgoing
    }

    describe("runOnce") {
      it("should take element and apply operation") {
        incoming.get.offer("One")

        operatorExecutor.runOnce()

        assert(outgoing.get.peek() == 3)
      }

      it("should no operation if incoming is empty") {
        operatorExecutor.runOnce()

        assert(outgoing.get.isEmpty)
      }
    }
  }
}
