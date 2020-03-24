package com.symbology.config

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class BaseSimulation extends Simulation {

  val userCount: Int = getProperty("USERS", "5").toInt
  val rampDuration: Int = getProperty("RAMP_DURATION", "10").toInt
  val testDuration: Int = getProperty("DURATION", "60").toInt

  // 1 Common HTTP Configuration
  val httpConf = http
    .baseUrl("http://computer-database.gatling.io/")
    .header("Accept", "application/json")
  //    .proxy(Proxy("localhost", 8888).httpsPort(8888))

  after {
    println("Stress test completed.")
  }

  private def getProperty(propertyName: String, defaultValue: String) = {
    Option(System.getenv(propertyName))
      .orElse(Option(System.getProperty(propertyName)))
      .getOrElse(defaultValue)
  }

}
