package ren.nicholas.streamwork.core

import org.scalatest.*
import org.scalatest.funspec.AnyFunSpec

import scala.collection.mutable

class OperatorExecutorTest extends AnyFunSpec with BeforeAndAfter {
  describe("A OperatorExecutor") {
    var operatorExecutor: OperatorExecutor[String, Int] = null
    before {
      operatorExecutor = new OperatorExecutor[String, Int](_.length)
    }

    describe("runOnce") {
      it("should take element and apply operation") {
        operatorExecutor.incoming.enqueue("One")

        operatorExecutor.runOnce()

        assert(operatorExecutor.outgoing.head == 3)
      }

      it("should no operation if incoming is empty") {
        operatorExecutor.runOnce()

        assert(operatorExecutor.outgoing.isEmpty)
      }
    }
  }
}
