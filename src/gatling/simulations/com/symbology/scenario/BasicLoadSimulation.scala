package com.symbology.scenario

import com.symbology.config.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

class BasicLoadSimulation extends BaseSimulation {

  val scn = scenario("Computer DB")
    .exec(getAllComputers)
    .pause(5)
    .exec(getSpecificComputer(2))
    .pause(5)
    .exec(getAllComputers)

  def getAllComputers: ChainBuilder = {
    exec(http("Check Computer DB")
      .get("/")
      .check(status.is(200)))
  }

  def getSpecificComputer(gameId: Long): ChainBuilder = {
    exec(http("Get Specific Game")
      .get(s"/computers/$gameId")
      .check(status.in(200 to 201)))
  }

  setUp(
    scn.inject(
      nothingFor(2),
      atOnceUsers(2),
      rampUsers(1) during 5
    )
  ).protocols(httpConf /*.inferHtmlResources()*/)
}
