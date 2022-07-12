package ren.nicholas.streamwork.core.executor

import org.scalatest.BeforeAndAfter
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should

import java.util.concurrent.ConcurrentLinkedQueue
import scala.jdk.CollectionConverters.*

class DispatcherTest extends AnyFunSpec with BeforeAndAfter with should.Matchers {
  describe("Dispatcher") {
    val incomingQueue: ConcurrentLinkedQueue[Int] = ConcurrentLinkedQueue[Int]()
    it("dispatch message by partition strategy") {
      val dispatcher = Dispatcher[Int](incomingQueue,
        parallelism = 3,
        strategyOpt = Some(_ % 2)
      )
      dispatcher.incoming.offer(1)
      dispatcher.incoming.offer(2)
      dispatcher.incoming.offer(3)
      dispatcher.incoming.offer(4)

      (1 to 4).foreach(_ => dispatcher.runOnce())

      dispatcher.outgoingAt(0).asScala.toList should contain allOf(2, 4)
      dispatcher.outgoingAt(1).asScala.toList should contain only(1, 3)
      dispatcher.outgoingAt(2).asScala.toList shouldBe empty
    }
  }

  describe("with default partition strategy") {
    val incomingQueue: ConcurrentLinkedQueue[Int] = ConcurrentLinkedQueue[Int]()
    it("dispatch message in round-robin style") {
      val dispatcher = Dispatcher[Int](
        incomingQueue,
        parallelism = 2
      )

      dispatcher.incoming.offer(1)
      dispatcher.incoming.offer(2)
      dispatcher.incoming.offer(3)
      dispatcher.incoming.offer(4)

      (1 to 4).foreach(_ => dispatcher.runOnce())

      dispatcher.outgoingAt(0).asScala.toList should contain allOf(2, 4)
      dispatcher.outgoingAt(1).asScala.toList should contain allOf(1, 3)
    }
  }
}
