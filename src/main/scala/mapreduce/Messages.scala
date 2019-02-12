package mapreduce

case class DoMap(jobName: String, pathIn: String, taskNumber: Int, fn: Mapper, nReduce: Int)

case class DoReduce(jobName: String, pathOut: String, taskNumber: Int, fn: Reducer, nMap: Int, hash: Int)
