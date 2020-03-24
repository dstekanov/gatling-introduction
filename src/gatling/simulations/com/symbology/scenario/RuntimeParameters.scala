package com.symbology.scenario

import com.symbology.config.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class RuntimeParameters extends BaseSimulation {

  def getAllVideoGames: ChainBuilder = {
    exec(http("Get All Video Games")
      .get("videogames")
      .check(status.is(200)))
  }

  val scn = scenario("Video Game DB")
    .forever() {
      exec(getAllVideoGames)
    }

  before {
    println(s"Running test with $userCount users")
    println(s"Rumping users over $rampDuration seconds")
    println(s"Total test duration $testDuration seconds")
  }

  setUp(
    scn.inject(
      nothingFor(5 seconds),
      rampUsers(userCount) during (rampDuration second)
    )
  ).protocols(httpConf).maxDuration(testDuration seconds)
}
