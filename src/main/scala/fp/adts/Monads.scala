package fp.adts

import cats.data.OptionT
import cats.implicits._
import scala.util.Try

object Monads {
  def main(args: Array[String]): Unit = {
    // Option monad
    val optionValue = Option(null); println(s"Option value ${optionValue}") // None, Some(null), null pointer exception

    val transformedOptionValue = optionValue.map(_.toString).orNull; println(s"Mapped option value ${transformedOptionValue}") // None, null pointer exception at toString, ""

    def generateRandomString: String = null
//    val optionFunctionWrapper = Option(generateRandomString.hashCode); println(s"Method result Option ${transformedOptionValue}") // None, null pointer exception at toString, some hashcode value

    val tryValue = Try(generateRandomString.hashCode); println("Try value :")

    // Try isn't a monad
    val x = Try(throw new Exception("first"))
      .map(_ => 2).toEither.fold(err => throw new Exception(err), identity)


  }
}
