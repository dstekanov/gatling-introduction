package simulations

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import config.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.util.Random

class CustomFeeder extends BaseSimulation {

  private val idNumber: Iterator[Int] = (16 to 20).iterator
  private val rnd = new Random()
  private val now = LocalDate.now()
  private val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  def randomString(length: Int): String = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  def getRandomDate(startDate: LocalDate, random: Random): String = {
    startDate.minusDays(random.nextInt(30)).format(pattern)
  }

  val customFeeder = Iterator.continually(Map(
    "gameId" -> idNumber.next(),
    "name" -> ("Game-" + randomString(5)),
    "releaseDate" -> getRandomDate(now, rnd),
    "reviewScore" -> rnd.nextInt(100),
    "category" -> ("Category-" + randomString(6)),
    "rating" -> ("Rating-" + randomString(4))
  ))

  def postNewGame() = {
    repeat(5) {
      feed(customFeeder).
        exec(http("Post New Game")
          .post("videogames")
          .body(ElFileBody("NewGameTemplate.json")).asJson
          .check(status.is(200))
          .check(bodyString.saveAs("responseBody")))
        .exec { session => println(session("responseBody").as[String]); session }
        .pause(1)
    }
  }

  val scn = scenario("Video Game DB")
    .exec(postNewGame)

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

}
