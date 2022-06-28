package ren.nicholas.streamwork.example

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should
import ren.nicholas.streamwork.core.stream.{Sink, Source, StreamBuilder}
import ren.nicholas.streamwork.core.Topology

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.duration.SECONDS

class SourceAndOperatorTest extends AnyFunSpec with should.Matchers {

  describe("simple stream run") {
    it("should read from source and send to sink") {
      val source: Source[Int] = Source.of(1, 2, 3, 4)
      val sink: Sink[Int] = Sink.empty()

      val streamBuilder: StreamBuilder = StreamBuilder()

      streamBuilder
        .source("numbers", source)
        .to("result", sink)

      val topology = streamBuilder.build()
      topology.run()
      Thread.sleep(1000)

      sink.all should contain allOf(1, 2, 3, 4)
    }

    it("should read from source and apply operator and send to sink") {
      val source: Source[Int] = Source.of(1, 2, 3, 4)
      val sink: Sink[Int] = Sink.empty()

      val streamBuilder: StreamBuilder = StreamBuilder()

      streamBuilder
        .source("numbers", source)
        .map("double", _ * 2)
        .to("result", sink)

      val topology = streamBuilder.build()
      topology.run()
      Thread.sleep(2000)

      sink.all should contain allOf(2, 4, 6, 8)
    }
  }
}
