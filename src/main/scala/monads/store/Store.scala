package monads.store

import monads.store.Store2.{OrderService, ProductService, UserService}

import scala.util.Try
//import cats.syntax.either._

object Store {
  private val productService = new ProductService(new ProductRepository)
  private val userService = new UserService(new UserRepository)
  private val orderService = new OrderService(new OrderRepository)

  def placeOrder(userId: String, productId: String, quantity: Int): Either[Throwable, Order] = {
    /*
  * 1/ Query the database for the product,
  * and update product quantity base on orders (also handle failures)
  * 2/ Create order
  * 3/ Send email to user about Order
  * */
    for {
      product <- productService.getProduct(productId)
        .filterOrElse(_.quantity > quantity, new Throwable("not sufficient product quantity"))
      user <- userService.getUser(userId)
      order <- Right(Order(productId, userId, quantity, quantity * product.price))
      email <- user.email.toRight(new Throwable("Empty Email"))
    } yield {
      orderService.addOrder(order)
      productService.unloadProduct(productId, quantity)
      Try(MailServer.sendMail(email, order))
      order
    }
  }

  /*
  * 1/ Query the database for the product,
  * and update product quantity base on orders (also handle failures)
  * 2/ Create order
  * 3/ Send email to user about Order
  * */
  def placeOrder2(userId: String, productId: String, quantity: Int) = ???

  def main(args: Array[String]): Unit = {
    val start = System.currentTimeMillis()
    println(placeOrder("1", "1", 3))
    val end = System.currentTimeMillis()
    println(s"Time ${end - start}")
  }


  class ProductService(productRepository: ProductRepository) {
    def getProduct(id: String): Either[Throwable, Product] =
      Try(productRepository.getProduct(id))
        .toEither

    def getProductWithFilter(id: String, f: Product => Boolean): Either[Throwable, Product] =
      Try(productRepository.getProduct(id))
        .toEither
        .filterOrElse(f, new Throwable)

    def unloadProduct(id: String, quantity: Int): Either[Throwable, Unit] =
      Try(productRepository.getProduct(id))
        .toEither
        .map { prod =>
          val newProd = prod.copy(quantity = quantity)
          productRepository.putProduct(newProd)
        }
  }

  class UserService(userRepository: UserRepository) {
    def getUser(id: String): Either[Throwable, User] = Try(userRepository.getUser(id)).toEither
  }

  class OrderService(orderRepository: OrderRepository) {
    def addOrder(order: Order): Unit = orderRepository.putOrder(order)
  }
}