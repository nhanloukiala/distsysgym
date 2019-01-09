package monads.store

import scala.concurrent.Future
import scala.util.Try
//import cats.syntax.either._

object Store {
  implicit val ec = scala.concurrent.ExecutionContext.global

  private val productService = new ProductService(new ProductRepository)
  private val userService = new UserService(new UserRepository)
  private val orderService = new OrderService(new OrderRepository)

  def placeOrder(userId: String, productId: String, quantity: Int): Future[Order] = {
    /*
  * 1/ Query the database for the product,
  * and update product quantity base on orders (also handle failures)
  * 2/ Create order
  * 3/ Send email to user about Order
  * */

    val userFuture = userService.getUser(userId)
    val productFuture = productService.getProduct(productId)

    for {
      product <- productFuture
        if product.quantity > quantity
      user <- userFuture
      order <- Future.successful(Order(productId, userId, quantity, quantity * product.price))
    } yield {
      Future(MailServer.sendMail(user.email.get, order))
      Future(orderService.addOrder(order))
      Future(productService.unloadProduct(productId, quantity))
      order
    }
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
    def getProduct(id: String): Future[Product] =
      Future {
        productRepository.getProduct(id)
      }

    def unloadProduct(id: String, quantity: Int): Future[Unit] =
      Future {
        productRepository.getProduct(id)
      }
  }

  class UserService(userRepository: UserRepository) {
    def getUser(id: String): Future[User] = Future {
      userRepository.getUser(id)
    }
  }

  class OrderService(orderRepository: OrderRepository) {
    def addOrder(order: Order): Future[Unit] = Future {
      orderRepository.putOrder(order)
    }
  }
}