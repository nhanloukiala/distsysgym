package fp.adts

object MonoidTypeClass {
  implicit val monoidInt = new Monoid[Int] {
    override def identity: Int = 0
    override def combine(first: Int, second: Int): Int = first + second
  }

  implicit val monoidString = new Monoid[String] {
    override def identity: String = ""
    override def combine(first: String, second: String): String = first + second
  }
//
//  implicit def monoidPair[T](implicit m: Monoid[T]) : Monoid[Pair[T]] = new Monoid[Pair[T]] {
//    override def identity: Pair[T] = Pair(m.identity)
//
//    override def combine(first: Pair[T], second: Pair[T]): Pair[T] = Pair(m.combine(first.bla, second.bla))
//  }


//  implicit class TupleTypeClass[A, B](in: (A, B))(implicit m1: Monoid[A], m2: Monoid[B]) {
//
//    def +(other: (A, B)) : (A, B) = (m1.combine(in._1, other._1), m2.combine(in._2, other._2))
//  }

//  implicit class TypeLevelLoop2[A](in: A)(implicit m: Monoid[A]) {
//    def implicitConversion2(b: A): A = m.combine(in, b)
//  }
//
//  implicit class TypeLevelLoop[A : Monoid](in: A)(implicit m: Monoid[A]) {
//    def implicitConversion(b: A): A = m.combine(in, b)
//  }

  implicit class TypeClassTuple2[A : Monoid, B: Monoid](in: (A, B))(implicit m1: Monoid[A], m2: Monoid[B]) {
    def +(other: (A, B)): (A, B) = {
      (m1.combine(in._1, other._1), m2.combine(in._2, other._2))
    }
  }

  implicit def tupleConversion[A, B](implicit m1: Monoid[A], m2: Monoid[B]) : Monoid[(A, B)] = new Monoid[(A, B)] {
    override def identity: (A, B) =  (m1.identity, m2.identity)

    override def combine(first: (A, B), second: (A, B)): (A, B) = (m1.combine(first._1, second._1), m2.combine(first._2, second._2))
  }

//  class Pair[T](val p1: String, val p2: T)

//  implicit class TypeClassPair[T](in: Pair[T])(implicit m1: Monoid[T]) {
//    def +(other: Pair[T]): Pair[T] = {
//      new Pair(in.p1 + other.p1, m1.combine(in.p2, other.p2))
//    }
//  }

  trait Monoid[T] {
    def identity: T

    def combine(first: T, second: T): T
  }

//  case class Pair[T](bla: T)

}


object Monoid {
  def main(args: Array[String]): Unit = {
    import MonoidTypeClass._

    (1, ((1,1), (1,2))) + (1, ((1,5), (1,2)))

    print((1, 1) + Pair(1, 2))
  }
}
