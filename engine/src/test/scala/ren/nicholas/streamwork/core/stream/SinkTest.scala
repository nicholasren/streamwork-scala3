package ren.nicholas.streamwork.core.stream

import org.scalatest.*
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers.*
import ren.nicholas.streamwork.core.stream.Sink

import scala.collection.mutable

class SinkTest extends AnyFunSpec {

  describe("InMemorySink") {

    it("should hold ingress data in memory") {
      val sink: Sink[String] = Sink.memory()
      sink.push("one")
      sink.push("two")
      sink.push("three")

      sink.all should contain allOf("one", "two", "three")
    }
  }
}
