package fp.adts

//object MonoidTypeClass {
//  implicit val monoidInt = new Monoid[Int] {
//    override def identity: Int = 0
//    override def combine(first: Int, second: Int): Int = first + second
//  }
//
//  implicit val monoidString = new Monoid[String] {
//    override def identity: String = ""
//    override def combine(first: String, second: String): String = first + second
//  }
//
//  implicit class TypeLevelLoop[A](in: A) {
//    def implicitConversion(b: A)(implicit m: Monoid[A]): A = m.combine(in, b)
//  }
//
//  implicit class TypeClassTuple2[A, B](in: (A, B)) {
//    def +(other: (A, B))(implicit m1: Monoid[A], m2: Monoid[B]): (A, B) = {
//      (m1.combine(in._1, other._1), m2.combine(in._2, other._2))
//    }
//  }
//
//  implicit def tupleCombine[A, B](implicit m1: Monoid[A], m2: Monoid[B]): Monoid[(A,B)] = new Monoid[(A, B)] {
//    override def identity: (A, B) = (m1.identity, m2.identity)
//    override def combine(first: (A, B), second: (A, B)): (A, B) = (m1.combine(first._1, second._1), m2.combine(first._2, second._2))
//  }
//
//  trait Monoid[T] {
//    def identity: T
//    def combine(first: T, second: T): T
//  }
//}
//
//
//object Monoids {
//  def main(args: Array[String]): Unit = {
//    import MonoidTypeClass._
//    (1, 2) + (2, 3)
//
//    (1, ((1,2), (1,3))) + (1, ((1,2), (1,4)))
//  }
//}
