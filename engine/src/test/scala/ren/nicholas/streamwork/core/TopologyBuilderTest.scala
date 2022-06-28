package ren.nicholas.streamwork.core

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should
import ren.nicholas.streamwork.core.stream.{Sink, Source, StreamBuilder}

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

  describe("source and operator should be connected via queue") {
    val source: Source[Int] = Source.of(1, 2, 3, 4)
    val sink: Sink[Int] = Sink.empty()
    val streamBuilder = StreamBuilder()

    streamBuilder
      .source("numbers", source) //Stream[Int]
      .map("double", _ * 2) //Stream[Int]
      .to("result", sink) //Unit

    val topology = streamBuilder.build()

    val sourceExecutor = topology.executorOf("numbers").get
    val operatorExecutor = topology.executorOf("double").get
    val sinkExecutor = topology.executorOf("result").get

    sourceExecutor.outgoing shouldBe operatorExecutor.incoming
    operatorExecutor.outgoing shouldBe sinkExecutor.incoming
  }
}
