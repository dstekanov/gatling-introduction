package com.symbology.config

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class BaseSimulation extends Simulation {

  val userCount: Int = getProperty("USERS", "5").toInt
  val rampDuration: Int = getProperty("RAMP_DURATION", "10").toInt
  val testDuration: Int = getProperty("DURATION", "60").toInt

  // 1 Common HTTP Configuration
  val httpConf: HttpProtocolBuilder = http
    // https://gatling.io/docs/current/quickstart
    .baseUrl("http://computer-database.gatling.io")
    .header("Accept", "application/json")

  after {
    println("Stress test completed.")
  }

  private def getProperty(propertyName: String, defaultValue: String) = {
    Option(System.getenv(propertyName))
      .orElse(Option(System.getProperty(propertyName)))
      .getOrElse(defaultValue)
  }

}
