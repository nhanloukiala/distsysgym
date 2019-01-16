package monads.store

import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

object Codecs {

  case class Product(`strange-field`: String,
                     `other-strange-field`: Option[String])

  def main(args: Array[String]): Unit = {
    val product = Product("first", Some("second"))
    println(product.asJson.noSpaces)

    val jsonString = List(
      """{"strange-field":"first","other-strange-field":null}""",
      """{"strange-field":"first"}""",
      """{"strange-field":"first"}"""
    )

    val decodedProduct = jsonString.map(decode[Product](_))
    println(decodedProduct)
  }
}
