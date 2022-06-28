package ren.nicholas.streamwork.core

import scala.collection.mutable

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers.*
import org.scalatest._

import ren.nicholas.streamwork.core.stream.InMemorySink

class SinkTest extends AnyFunSpec {

  describe("InMemorySink") {

    it("should hold ingress data in memory") {
      val sink = InMemorySink[String]()
      sink.push("one")
      sink.push("two")
      sink.push("three")

      sink.all should contain allOf("one", "two", "three")
    }
  }
}
