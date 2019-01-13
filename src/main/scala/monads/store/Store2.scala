//package monads.store
//
//import scala.util.Try
//import cats.syntax.either._
//object Store2 {
//  private val productService = new ProductService(new ProductRepository)
//  private val userService = new UserService(new UserRepository)
//  private val orderService = new OrderService(new OrderRepository)
//
//  def placeOrder(userId: String, productId: String, quantity: Int): Either[Throwable, Order] = {
//    for {
//      user <- userService.getUser(userId)
//      product <- productService.getProduct(productId)
//        .filterOrElse(_.quantity > quantity, new Throwable("not sufficient product quantity"))
//      order <- Right(Order(productId, userId, quantity, product.price * quantity))
//      _ <- Right(orderService.addOrder(order))
//    } yield order
//  }
//
//  /*
//  * 1/ Query the database for the product,
//  * and update product quantity base on orders (also handle failures)
//  * 2/ Create order
//  * 3/ Send email to user about Order
//  * */
//  def placeOrder2(userId: String, productId: String, quantity: Int) = ???
//
//  def main(args: Array[String]): Unit = {
//    placeOrder("1", "1", 3)
//    println(placeOrder("1", "1", 100))
//    println(placeOrder("7", "5", 3))
//  }
//
//  class ProductService(productRepository: ProductRepository) {
//    def getProduct(id: String): Either[Throwable, Product] =
//      Try(productRepository.getProduct(id))
//        .toEither
//
//    def getProductWithFilter(id: String, f: Product => Boolean): Either[Throwable, Product] =
//      Try(productRepository.getProduct(id))
//        .toEither
//      .filterOrElse(f, new Throwable)
//
//    def unloadProduct(id: String, quantity: Int): Either[Throwable, Unit] =
//      Try(productRepository.getProduct(id))
//        .toEither
//        .map { prod =>
//          val newProd = prod.copy(quantity = quantity)
//          productRepository.putProduct(newProd)
//        }
//  }
//
//  class UserService(userRepository: UserRepository) {
//    def getUser(id: String): Either[Throwable, User] = Try(userRepository.getUser(id)).toEither
//  }
//
//  class OrderService(orderRepository: OrderRepository) {
//    def addOrder(order: Order): Unit = orderRepository.putOrder(order)
//  }
//}
//
