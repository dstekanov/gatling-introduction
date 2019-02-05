import config.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class MyFirstTest extends BaseSimulation {

  // Scenario Definition
  val scn = scenario("My first test")
    .exec(http("Get All Games")
      .get("videogames"))

  // Load Scenario
  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

}
