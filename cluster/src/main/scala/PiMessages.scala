package pfe.cluster

//import akka.util.Duration
import concurrent.duration.Duration

sealed trait PiMessage
case class Calculate(numTerms: Int) extends PiMessage
case class Work(start: Int, numTerms: Int) extends PiMessage
case class Result(value: Double) extends PiMessage
case class PiApproximation(pi: Double, duration: Duration)
