package simulations

import config.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.core.feeder.SourceFeederBuilder
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

class CsvFeeder extends BaseSimulation {

  val csvFeeder: SourceFeederBuilder[String] = csv("game.csv").circular

  def getSpecificVideoGame: ChainBuilder = {
    repeat(10) {
      feed(csvFeeder).
        exec(http("Get Specific Video Game")
          .get("videogames/${id}")
          .check(jsonPath("$.name").is("${name}"))
          .check(status.is(200)))
        .pause(1)
    }
  }

  val scn = scenario("Video Game DB")
    .exec(getSpecificVideoGame)

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

}
