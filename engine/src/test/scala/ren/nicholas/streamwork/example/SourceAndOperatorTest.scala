package ren.nicholas.streamwork.example

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should

import ren.nicholas.streamwork.core.Source
import ren.nicholas.streamwork.core.Sink
import ren.nicholas.streamwork.core.Topology


class SourceAndOperatorTest extends AnyFunSpec with should.Matchers {

  describe("simple stream run") {
    it("should read from source and apply operator") {
      val source: Source[Int] = Source.of(1, 2, 3, 4)
      val sink: Sink[Int] = Sink.empty()

      val stream = Topology
        .source("numbers", source)
        .map("double", (x: Int) => x * 2)
        .to("result", sink)

      Topology.run(stream)

      sink.all should contain allOf(2, 4, 6, 8)
    }
  }

}
