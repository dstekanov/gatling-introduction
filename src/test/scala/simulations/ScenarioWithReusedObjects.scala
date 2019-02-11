package simulations

import config.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class ScenarioWithReusedObjects extends BaseSimulation {

  val scn = scenario("Video Game DB")
    .exec(VideoGames.allVideoGames)
    .exec(SpecificGame.specificGame)
    .exec(VideoGames.allVideoGames)

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)


  object VideoGames {

    val allVideoGames = exec(http("Get All Video Games")
      .get("videogames"))
      .pause(5)

  }

  object SpecificGame {

    val specificGame = exec(http("Get specific game")
      .get("videogames/1"))
      .pause(5)

  }

}
