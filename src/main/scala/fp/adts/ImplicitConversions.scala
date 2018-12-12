package fp.adts

// Decorating classes from external libraries
object ExtensionMethods {
  class LibList[T <: AnyVal : Ordering](val list: List[T]) {
    def sortedAsc() : List[T] = list.sorted
  }

  implicit class LibListOps[T <: AnyVal : Ordering](libList: LibList[T]) {
    def sortedDesc() : List[T] = libList.sortedAsc.reverse
  }

  def main(args: Array[String]): Unit = {
    val l1 = new LibList[Int](List(3,2,5,1))

    println(l1.sortedDesc)
  }
}

// instead of print(l1.sortedAsc.print)
object ExtensionMethods2 {
  import ExtensionMethods._

  implicit class ListOps[T](list: List[T]) {
    def print() : Unit = println(list.toString())
  }

  def main(args: Array[String]): Unit = {
    val l1 = new LibList[Int](List(3,2,5,1))

    l1.sortedAsc.print // instead of print(l1.sortedAsc.print)
  }
}


