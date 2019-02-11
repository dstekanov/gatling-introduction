package simulations

import config.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class RuntimeParameters extends BaseSimulation {

  private def getProperty(propertyName: String, defaultValue: String) = {
    Option(System.getenv(propertyName))
      .orElse(Option(System.getProperty(propertyName)))
      .getOrElse(defaultValue)
  }

  private val userCount: Int = getProperty("USERS", "5").toInt
  private val rampDuration: Int = getProperty("RAMP_DURATION", "10").toInt
  private val testDuration: Int = getProperty("DURATION", "60").toInt

  before {
    println(s"Running test with $userCount users")
    println(s"Rumping users over $rampDuration seconds")
    println(s"Total test duration $testDuration seconds")
  }

  def getAllVideoGames: ChainBuilder = {
    exec(http("Get All Video Games")
      .get("videogames")
      .check(status.is(200)))
  }

  val scn = scenario("Video Game DB")
    .forever() {
      exec(getAllVideoGames)
    }

  setUp(
    scn.inject(
      nothingFor(5 seconds),
      rampUsers(userCount) during (rampDuration second)
    )
  ).protocols(httpConf).maxDuration(testDuration seconds)
}
