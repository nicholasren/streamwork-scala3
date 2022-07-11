import ren.nicholas.streamwork.core.stream.*
import ren.nicholas.streamwork.core.topology.Topology

import scala.util.Random.{nextInt, shuffle}

case class Person(name: String, age: Int, isHost: Boolean)

object MyApp extends App {
  private val streamBuilder = StreamBuilder()

  val people = List(
    Person("Bernard", 55, true),
    Person("Dolores", 35, true),
    Person("Maeve", 5, true),
    Person("Liam", 55, false),
    Person("Teddy", 35, true)
  )

  streamBuilder
    .source("source", Source.continually(shuffle(people).head))
    .map("change age", p => Person(p.name, nextInt(100), p.isHost))
    .filter("is host", _.isHost)
    .to("print", Sink.console())

  val topology: Topology = streamBuilder.build()
  topology.run()
  
}
