import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder
import simulations.CsvFeeder

object GatlingRunner {

  def main(args: Array[String]): Unit = {

    val simClass = classOf[CsvFeeder].getName

    val props = new GatlingPropertiesBuilder
    props.simulationClass(simClass)

    Gatling.fromMap(props.build)
  }

}
