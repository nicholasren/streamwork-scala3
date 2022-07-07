import ren.nicholas.streamwork.core.stream.*
import ren.nicholas.streamwork.core.topology.Topology

object MyApp extends App {
  private val streamBuilder = StreamBuilder()

  case class Person(name: String, age: Int, isHost: Boolean)

  val people = List(
    Person("Bernard", 55, true),
    Person("Dolores", 35, true),
    Person("Maeve", 5, true),
    Person("Liam", 55, false),
    Person("Teddy", 35, true)
  )

  streamBuilder
    .source("source", Source.of(people: _*))
    .filter("isHost", _.isHost)
    .to("print", Sink.console())

  val topology: Topology = streamBuilder.build()
  topology.run()
}
