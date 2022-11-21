package ren.nicholas.streamwork.core.topology

import org.scalatest.BeforeAndAfter
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should
import ren.nicholas.streamwork.core.stream.{MemSink, Sink, Source, StreamBuilder}
import ren.nicholas.streamwork.core.topology.Topology

import scala.compiletime.uninitialized
import scala.concurrent.Await
import scala.concurrent.duration.{Duration, SECONDS}

class TopologyTest extends AnyFunSpec with should.Matchers with BeforeAndAfter {
  var source: Source[Int] = uninitialized
  var streamBuilder: StreamBuilder = uninitialized

  before {
    source = Source.of(1, 2, 3, 4)
    streamBuilder = StreamBuilder()
  }

  describe("single partition stream run") {
    it("should read from source and send to sink") {
      val sink: MemSink[Int] = Sink.memory()

      streamBuilder
        .source("numbers", source)
        .to("result", sink)
      val topology = streamBuilder.build()
      topology.run()
      sink.blockingForN(4)
      sink.all should contain allOf(1, 2, 3, 4)
    }

    it("should read from source and apply operator and send to sink") {
      val sink: MemSink[Int] = Sink.memory()

      streamBuilder
        .source("numbers", source)
        .map("double", _ * 2)
        .to("result", sink)

      val topology = streamBuilder.build()

      topology.run()
      sink.blockingForN(4)

      sink.all should contain allOf(2, 4, 6, 8)
    }

    it("should read from source and apply operators and send to sink") {
      val sink: MemSink[String] = Sink.memory()

      streamBuilder
        .source("numbers", source)
        .map("double", _ * 2)
        .map("to_string", _.toString)
        .to("result", sink)
      val topology = streamBuilder.build()

      topology.run()

      sink.blockingForN(4)
      sink.all should contain allOf("2", "4", "6", "8")
    }

    it("should read from source and apply filter and send to sink") {
      val sink: MemSink[Int] = Sink.memory()

      streamBuilder
        .source("numbers", source)
        .map("double", _ * 2)
        .filter("greater_than_five", _ > 5)
        .to("result", sink)
      val topology = streamBuilder.build()
      topology.run()

      sink.blockingForN(2)
      sink.all should contain allOf(6, 8)
    }
  }

  describe("multiple partition stream run") {
    it("should read from source and send to sink") {
      val sink: MemSink[Int] = Sink.memory()

      streamBuilder
        .source("numbers", source, 2)
        .to("result", sink)
      val topology = streamBuilder.build()
      topology.run()

      sink.blockingForN(4)

      sink.all should contain allOf(1, 2, 3, 4)
    }
  }

  describe("continuous streaming") {
    it("should only stop when required") {
      val source: Source[Int] = Source.continually(scala.util.Random.nextInt(100))
      val sink = Sink.memory[Int]()
      streamBuilder
        .source("flow of ints", source)
        .to("end", sink)

      val topology = streamBuilder.build()

      topology.run()

      sink.blockingForN(100)
      topology.stop()
      sink.all.forall(_ < 100) shouldBe true
    }
  }
}
