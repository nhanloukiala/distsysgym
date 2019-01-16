package mapreduce

trait Mapper {
  def map(key: String, value: String): (String, String)
}

trait Reducer {
  def reducer(key: String, value: Iterable[String]): (String, Iterable[String])
}

trait
