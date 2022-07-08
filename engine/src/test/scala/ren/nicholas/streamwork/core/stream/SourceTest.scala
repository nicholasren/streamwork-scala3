package ren.nicholas.streamwork.core.stream

import org.scalatest.funspec.AnyFunSpec

import scala.util.Random.nextInt

class SourceTest extends AnyFunSpec {

  describe("Source") {
    describe("from a computation") {
      it("should emit data by evaluating each  computation") {
        val source = Source.continually[Int](nextInt(1000))
        (1 to 100).foreach(_ =>
          assert(source.get < 1000)
        )
      }
    }
  }
}
