package com.symbology.scenario

import com.symbology.config.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

class CsvFeederToCustom extends BaseSimulation {

  val idNumber: Iterator[Int] = (1 to 10).iterator

  def getNextGameId = Map("id" -> idNumber.next())

  val customFeeder = Iterator.continually(getNextGameId)

  def getSpecificVideoGame: ChainBuilder = {
    repeat(10) {
      feed(customFeeder).
        exec(http("Get Specific Video Game")
          .get("videogames/${id}")
          .check(status.is(200))
          .check(bodyString.saveAs("responseBody")))
        .pause(1)
        .exec { session => println(session("responseBody").as[String]); session }
    }
  }

  val scn = scenario("Video Game DB")
    .exec(getSpecificVideoGame)

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

}
