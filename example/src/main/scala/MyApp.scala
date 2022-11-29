import ren.nicholas.streamwork.core.stream.*
import ren.nicholas.streamwork.core.topology.Topology
import com.github.javafaker.Faker

object MyApp extends App {
  private val streamBuilder = StreamBuilder()

  private val faker = Faker()

  streamBuilder
    .source("source", Source.continually(faker.name().name()))
    .map("to person", name => Person(name, faker.number().numberBetween(5, 100)))
    .to("print", Sink.console())

  val topology: Topology = streamBuilder.build()

  topology.run()

  Thread.sleep(3000)
  topology.stop()
}
