import ren.nicholas.streamwork.core.stream.*
import ren.nicholas.streamwork.core.topology.Topology
import com.github.javafaker.Faker
import scala.util.Random.{nextInt}

case class Person(name: String, age: Int, isHost: Boolean)

object MyApp extends App {
  private val streamBuilder = StreamBuilder()

  private val faker = Faker()

  val people = List(
    Person("Bernard", 55, true),
    Person("Dolores", 35, true),
    Person("Maeve", 5, true),
    Person("Liam", 55, false),
    Person("Teddy", 35, true)
  )


  streamBuilder
    .source("source", Source.continually {
      Person("1", nextInt, true)
    }, 2)
    .map("change age", p => Person(p.name, nextInt(100), nextInt(100) % 3 == 0))
    .filter("is host", _.isHost)
    .to("print", Sink.console())

  val topology: Topology = streamBuilder.build()
  topology.run()

  Thread.sleep(30000)
  topology.stop()
}
