package ren.nicholas.streamwork.core

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should

class TopologyBuilderTest extends AnyFunSpec with should.Matchers {

  describe("source and sink should be connected via queue") {
    val source: Source[Int] = Source.of(1, 2, 3, 4)
    val sink: Sink[Int] = Sink.empty()
    val streamBuilder = StreamBuilder()

    streamBuilder
      .source("numbers", source) //Stream[Int]
      .to("result", sink) //Unit

    val topology = streamBuilder.build()

    topology.executorOf("numbers").get.outgoing shouldBe topology.executorOf("result").get.incoming
  }
}
