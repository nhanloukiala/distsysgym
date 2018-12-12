package fp.adts

import cats.data.State

object StateMonad {
  type SST = State[ServerState, Unit]
  // Leader, Follower, Candidate
  sealed trait ServerState
  case object Leader extends ServerState
  case object Follower extends ServerState
  case object Candidate extends ServerState

  case class Server(state: ServerState)

  def main(args: Array[String]): Unit = {
//    val becomeCandidate : SST = State { serverState =>
//      serverState match {
//        case Follower => (Candidate, ())
//          println("becomeCandidate")
//        case others => (others, ())
//      }
//    }
//
//    val becomeLeader: SST = State { serverState =>
//      serverState match {
//        case Candidate => (Leader, ())
//          println("becomeLeader")
//        case others => (others, ())
//      }
//    }

    val backToCandidate: SST = State { serverState =>
      serverState match {
        case Candidate =>
          println("backToCandidate")
          (Follower, ())
        case others => (others, ())
      }
    }


  }
}
