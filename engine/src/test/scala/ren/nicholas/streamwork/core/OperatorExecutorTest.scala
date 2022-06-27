package ren.nicholas.streamwork.core

import org.scalatest.matchers.should
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.BeforeAndAfter

import scala.collection.mutable

class OperatorExecutorTest extends AnyFunSpec with BeforeAndAfter with should.Matchers {
  describe("A OperatorExecutor") {
    var operatorExecutor: OperatorExecutor[String, Int] = null
    var incoming: mutable.Queue[String] = null
    var outgoing: mutable.Queue[Int] = null

    before {
      incoming = mutable.Queue.empty
      outgoing = mutable.Queue.empty
      operatorExecutor = new OperatorExecutor[String, Int](incoming, outgoing, _.length)
    }

    describe("runOnce") {
      it("should take element and apply operation") {
        incoming.enqueue("One")

        operatorExecutor.runOnce()

        assert(outgoing.head == 3)
      }

      it("should no operation if incoming is empty") {
        operatorExecutor.runOnce()

        assert(outgoing.isEmpty)
      }
    }
  }
}
