package fp.adts
import cats.data.OptionT

import scala.util.{Failure, Success, Try}
import cats.syntax.either._


object EffectStacking {
  sealed trait Eff
  case object Err extends Eff

  type Result[A] = Either[Eff, Option[A]]

  object EitherOps {
    def lift[T](f: => T) : Result[T] = Try(f) match {
      case Success(s) => Either.right(Some(s))
      case Failure(err) => Either.left(Err)
    }
  }

  def unsafeCall() : String = {
    import scala.util.Random._
    (nextInt() % 2) match {
      case 0 => throw new Exception("Exception")
      case 1 => "na"
      case -1 => ""
    }
  }

  def main(args: Array[String]): Unit = {
    import EitherOps._

    (1 to 100).map { _ =>
      for {
        x1 <- lift(unsafeCall())
        x2 <- lift(unsafeCall())
        x3 <- lift(unsafeCall())
      } yield {
        println(s"${x1.orNull} ${x2.orNull} ${x3.orNull}")
      }
    }
  }

  def first(s: String) : Either[String, String] = Right(s)
  def second() : Either[String, String] = Right("")

  (for { s <- second
    s1 <- first(s)
  } yield s1).fold(err => throw new Exception(err), identity)
}
