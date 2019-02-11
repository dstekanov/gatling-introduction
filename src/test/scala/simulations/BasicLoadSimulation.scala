package simulations

import config.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

class BasicLoadSimulation extends BaseSimulation {

  val scn = scenario("Video Game DB")
    .exec(getAllVideoGames)
    .pause(5)
    .exec(getSpecificVideoGame(2))
    .pause(5)
    .exec(getAllVideoGames)

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

  setUp(
    scn.inject(
      nothingFor(5),
      atOnceUsers(10),
      rampUsers(10) during 5
    )
  ).protocols(httpConf /*.inferHtmlResources()*/)
}
