import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder
import simulations.BasicLoadSimulation

object GatlingRunner {

  def main(args: Array[String]): Unit = {

    val simClass = classOf[BasicLoadSimulation].getName

    val props = new GatlingPropertiesBuilder
    props.simulationClass(simClass)

    Gatling.fromMap(props.build)
  }

}
