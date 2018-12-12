package fp.adts

import scala.util.Random

trait Printable[T] {
  def prettyPrint(in : T) : String
}

object Printable{
  implicit val intPrint : Printable[Int] = new Printable[Int] {
    override def prettyPrint(in: Int): String = "pretty Int " + in
  }

  implicit val boolPrint : Printable[Boolean] = new Printable[Boolean] {
    override def prettyPrint(in: Boolean): String = "pretty Boolean " + in
  }

  def by[T](func: T => String): Printable[T] = new Printable[T] {
    override def prettyPrint(in: T): String = func(in)
  }
}

object TypeClass {
  implicit class PrettyList[T](in: List[T]) {
    def prettyPrint(implicit printable: Printable[T]): String = {
      in.map(printable.prettyPrint(_)).toString()
    }
  }

  def main(args: Array[String]): Unit = {
    val sampleInts = new PrettyList((1 to 5).toList)

    import Printable._
    println(sampleInts.prettyPrint)

    case class Foo(bar1: String, bar2: Int)
    val foos = new PrettyList(List(Foo("foo1", 1), Foo("foo2", 2)))

    println(foos.prettyPrint(Printable.by("int " + _.bar2.toString)))

    println(foos.prettyPrint(Printable.by(_.bar1)))

    (1 to 5).toList.prettyPrint
  }
}
