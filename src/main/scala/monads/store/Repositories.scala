package monads.store

import scala.collection.mutable
import scala.util.Random

case class Product(id: String, name: String, quantity: Int, price: Double)

case class User(id: String, name: String, email: Option[String])

case class Order(productId: String, userId: String, quantity: Int, total: Double)

class ProductRepository {
  private val database = mutable.Map(
    "1" -> Product("1", "Pen", 4, 1.0),
    "2" -> Product("2", "Notebook", 6, 3.0),
    "3" -> Product("3", "Sticky Note", 1, 5.0)
  )

  def getProduct(id: String): Product = {
    Thread.sleep(1000)
    database.getOrElse(id, throw new Exception(s"missing product ${id}"))
  }

  def putProduct(product: Product): Unit = {
    Thread.sleep(1000)
    (database += (product.id -> product))}
}

class UserRepository {
  private val database = mutable.Map(
    "1" -> User("1", "Nhan", Some("nhant@spotify.com")),
    "2" -> User("2", "Hawi", None),
    "3" -> User("3", "Camilo", None),
    "4" -> User("4", "Andrew", None),
    "5" -> User("5", "Ivan", None)
  )

  def getUser(id: String): User = {
    Thread.sleep(1000)
    database.getOrElse(id, throw new Exception(s"missing user ${id}"))
  }
}

class OrderRepository {
  private val database = mutable.Map.empty[String, Product]

  def getOrder(id: String): Order = {
    Thread.sleep(1000)
    null
  }

  def putOrder(order: Order): Order = {
    Thread.sleep(1000)
    null
  }
}

object MailServer {
  def sendMail(recepient: String, order: Order): Boolean = {
    Thread.sleep(1000)
    if (Random.nextInt() % 2 == 0) true else throw new Exception}
}
