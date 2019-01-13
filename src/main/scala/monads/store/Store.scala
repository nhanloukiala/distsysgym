package monads.store

import cats.data.EitherT
import scala.concurrent.Future
import io.circe._, io.circe.parser._
import io.circe.generic.auto._
import cats.implicits._

object Store {
  implicit val ec = scala.concurrent.ExecutionContext.global

  private val productService = new ProductService(new ProductRepository)
  private val userService = new UserService(new UserRepository)
  private val orderService = new OrderService(new OrderRepository)

  def placeOrder(userId: String, productId: String, quantity: Int): Future[Either[Error, Order]] = {
    /*
  * 1/ Query the database for the product,
  * and update product quantity base on orders (also handle failures)
  * 2/ Create order
  * 3/ Send email to user about Order
  * */

    val userFuture = userService.getUser(userId)
    val productFuture = productService.getProduct(productId)

    (for {
      product <- EitherT(productFuture)
      user <- EitherT(userFuture)
      order <- EitherT.rightT[Future, Error](Order(productId, userId, quantity, quantity * product.price))
    } yield {
      Future(MailServer.sendMail(user.email.get, order))
      Future(orderService.addOrder(order))
      Future(productService.unloadProduct(productId, quantity))
      order
    }).value
  }

  /*
  * 1/ Query the database for the product,
  * and update product quantity base on orders (also handle failures)
  * 2/ Create order
  * 3/ Send email to user about Order
  * */
  def main(args: Array[String]): Unit = {
    val start = System.currentTimeMillis()
    placeOrder("1", "1", 1)
      .onComplete { x =>
        println(x)
        val end = System.currentTimeMillis()
        println(s"Time ${end - start}")
      }

    Thread.sleep(100000)
  }

  class ProductService(productRepository: ProductRepository) {
    def getProduct(id: String): Future[Either[Error, Product]] =
      Future {
        decode[Product](productRepository.getProduct(id).body)
      }

    def unloadProduct(id: String, quantity: Int): Future[Unit] =
      Future {
        productRepository.getProduct(id)
      }
  }

  class UserService(userRepository: UserRepository) {
    def getUser(id: String): Future[Either[Error, User]] = Future {
      decode[User](userRepository.getUser(id).body)
    }
  }

  class OrderService(orderRepository: OrderRepository) {
    def addOrder(order: Order): Future[Unit] = Future {
      orderRepository.putOrder(order)
    }
  }
}