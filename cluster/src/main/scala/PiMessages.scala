package pfe.cluster

import akka.util.Duration
import akka.util.duration._

sealed trait PiMessage
case class Calculate(numTerms: Int) extends PiMessage
case class Work(start: Int, numTerms: Int) extends PiMessage
case class Result(value: Double) extends PiMessage
case class PiApproximation(pi: Double, duration: Duration)
