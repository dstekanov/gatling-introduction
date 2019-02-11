package simulations

import config.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class FixedDurationLoadSimulation extends BaseSimulation {

  def getAllVideoGames: ChainBuilder = {
    exec(http("Get All Video Games")
      .get("videogames")
      .check(status.is(200)))
  }

  def getSpecificVideoGame(gameId: Long): ChainBuilder = {
    exec(http("Get Specific Game")
      .get(s"videogames/$gameId")
      .check(status.in(200 to 201)))
  }

  val scn = scenario("Video Game DB")
    .forever() {
      exec(getAllVideoGames)
        .pause(5)
        .exec(getSpecificVideoGame(2))
        .pause(5)
        .exec(getAllVideoGames)
    }

  setUp(
    scn.inject(
      nothingFor(5 seconds),
      atOnceUsers(10),
      rampUsers(50) during (30 seconds)
    ).protocols(httpConf.inferHtmlResources())
  ).maxDuration(1 minute)
}
