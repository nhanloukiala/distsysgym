//package fp.adts
//
//import java.time.OffsetDateTime
//import java.util.concurrent.Executors
//
//import com.typesafe.config.ConfigFactory
//
//import scala.collection.JavaConverters._
//import scala.concurrent.duration.Duration
//import scala.concurrent.{Await, ExecutionContext, Future}
//import scala.sys.process._
//import scala.util.{Failure, Success}
//
//object Unpublish {
//  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(4))
//  val cf = ConfigFactory.load()
//
//  val start = "20181001"
//  val end = "20181031"
//
//  def main(args: Array[String]): Unit = {
//    val x = cf.getStringList("endpoints")
//      .asScala
//      .map(endpoint => Future {
//        s"hades partitions ${endpoint}" !!
//      })
//      .map { futureRow =>
//        futureRow.map { row =>
//          println(row)
//
//          Future {
//            row.split("\n")
//              .drop(1)
//              .map { partition =>
//                Future {
//                  val arr = partition
//                    .split(" ")
//                    .filter(!_.equals(""))
//                  val (ep, pt) = (arr(0), arr(1).substring(0, 10))
//
//                  if (pt >= start && pt <= end) {
//                    hadesLs(ep, pt)
//                      .map(rev =>
//                        unpublish(ep, pt, rev)
//                      )
//                  }
//                } onFailure { case err =>
//                  println(err.getMessage + "ha")
//                  Success("")
//                }
//              }
//          }
//        }
//
//        OffsetDateTime.parse("").toLocalDate
//      }.map(_.flatMap(identity)
//      .map(Success(_))
//      .recover { case t => Failure(t) })
//
//    Await.ready(Future.sequence(x.toSeq), Duration.Inf)
//  }
//
//  //revision
//  def hadesLs(ep: String, part: String): List[String] = {
//    println(s"hades ls ${ep} ${part}")
//    (s"hades ls ${ep} ${part}" !!)
//      .split("\n")
//      .drop(1)
//      .map(_.split(" ")(0))
//      .toList
//  }
//
//  def unpublish(ep: String, pt: String, rev: String): Unit = {
//    println(s"hades remove ${ep} ${pt} ${rev}")
//    //    (s"hades remove ${ep} ${pt} ${rev}".!!)
//    //      .map(println)
//  }
//}
