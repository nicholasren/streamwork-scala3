package ren.nicholas.streamwork.example

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should
import ren.nicholas.streamwork.core.{Sink, Source, StreamBuilder, Topology}


class SourceAndOperatorTest extends AnyFunSpec with should.Matchers {

  describe("simple stream run") {
    it("should read from source and apply operator") {
      val source: Source[Int] = Source.of(1, 2, 3, 4)
      val sink: Sink[Int] = Sink.empty()

      val streamBuilder: StreamBuilder = StreamBuilder()

      streamBuilder
        .source("numbers", source)
        .to("result", sink)

      val topology = streamBuilder.build()

      //TODO: run topology

      sink.all should contain allOf(1, 2, 3, 4)
    }
  }

}
