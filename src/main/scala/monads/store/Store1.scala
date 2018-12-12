package monads.store

import scala.util.{Try}

object Store1 {
  private val productService = new ProductService(new ProductRepository)
  private val userService = new UserService(new UserRepository)
  private val orderService = new OrderService(new OrderRepository)

  def placeOrder(userId: String, productId: String, quantity: Int): Option[Order] = {
    for {
      product <- productService.getProduct(productId) if (product.quantity > quantity)
      order <- Some(Order(productId, userId, quantity, product.price * quantity))
      _ <- orderService.addOrder(order)
      user <- userService.getUser(userId)
    } yield order
  }

  /*
  * 1/ Query the database for the product,
  * and update product quantity base on orders (also handle failures)
  * 2/ Create order
  * 3/ Send email to user about Order
  * */
  def placeOrder2(userId: String, productId: String, quantity: Int) = ???

  def main(args: Array[String]): Unit = {
    println(placeOrder("1", "1", 3))
    println(placeOrder("1", "1", 100))
    println(placeOrder("1", "5", 3))
  }

  class ProductService(productRepository: ProductRepository) {
    def getProduct(id: String): Option[Product] =
      Try(productRepository.getProduct(id))
        .toOption

    def unloadProduct(id: String, quantity: Int): Option[Unit] =
      Try(productRepository.getProduct(id))
        .toOption
        .map { prod =>
          val newProd = prod.copy(quantity = quantity)
          productRepository.putProduct(newProd)
        }
  }

  class UserService(userRepository: UserRepository) {
    def getUser(id: String): Option[User] = Try(userRepository.getUser(id)).toOption
  }

  class OrderService(orderRepository: OrderRepository) {
    def addOrder(order: Order): Option[Unit] = Some(orderRepository.putOrder(order))
  }

}

/*
* 1/ Have a layer which use monads to wrap-up external world's side effects (exceptions).
* 2/ All functions MUST return `Monad[T]`, instead of just value `T`.
* 3/ Don't open monads until the very last steps of the program. Use for-comprehension to pipe them around instead.
* 4/ Favor for comprehensions instead of pattern matching.
* 5/ Try & Futures aren't monad.
* */