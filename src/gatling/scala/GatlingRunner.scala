import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder
import simulations.FixedDurationLoadSimulation

object GatlingRunner {

  def main(args: Array[String]): Unit = {

    val simClass = classOf[FixedDurationLoadSimulation].getName

    val props = new GatlingPropertiesBuilder
    props.simulationClass(simClass)

    Gatling.fromMap(props.build)
  }

}
