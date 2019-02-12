package mapreduce

import java.io.{BufferedWriter, File, FileWriter}
import akka.actor.Actor
import scala.io.Source

trait Mapper {
  def map(key: String, value: String): Seq[(String, String)]
}

trait Reducer {
  def reducer(key: String, value: List[String]): (String, List[String])
}

case class Job(name: String, map: Mapper, reduce: Reducer, nM: Int, nR: Int)

class WorkerActor extends Actor {
  override def receive: Receive = {
    case DoMap(name, path, taskNo, mapper, nR) =>
      val paths = (0 to nR - 1).map { n =>
        val file = new File(bufferedPath(name, taskNo, n))
        val bw = new BufferedWriter(new FileWriter(file))
      }.toArray[BufferedWriter]

      Source.fromFile(path).getLines.map { line =>
        mapper
          .map("", line)
          .map { case (k, v) =>
            paths(hashKey(k)).write(s"${k},${v}")
          }
      }

      paths.map(_.close)


    case DoReduce(name, path, taskNo, reducer, nM, hash) =>
      val file = new File(bufferedPath(name, taskNo, hash))
      val bw = new BufferedWriter(new FileWriter(file))

      Source.fromFile(bufferedPath(name, taskNo, hash))
        .getLines
        .map { r =>
          val pair = r.split(",")
          pair(0) -> pair(1)
        }
        .toList
        .groupBy(_._1)
        .mapValues(_.map(_._2))
        .map { case (k, v) =>
          val out = reducer.reducer(k, v)
          bw.write(out.toString)
        }

      bw.close
  }

  def hashKey(in: String): Int = 1

  private def bufferedPath(jobName: String, nR: Int, taskNo: Int) = s"${jobName}_${taskNo}_${nR}"
  private def outputPath(jobName: String, nR: Int, taskNo: Int) = bufferedPath(jobName, nR, taskNo) + "_out"
}


class Master extends Actor {
  override def receive: Receive = {
    case Job(name, mapper, reducer, nMap, nReduce) =>

  }
}

