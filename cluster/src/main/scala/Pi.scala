package pfe.cluster

import akka.actor.{Actor, PoisonPill}
import Actor._
import akka.dispatch.Dispatchers
import com.typesafe.config.ConfigFactory
import akka.actor.{ ActorRef, Props, Actor, ActorSystem }

import java.util.concurrent.CountDownLatch
  /*
object PiClient extends App {

  calculate(nrOfWorkers = 4, nrOfElements = 10000, nrOfMessages = 10000)
   
  def calculate(nrOfWorkers: Int, nrOfElements: Int, nrOfMessages: Int) {
    val system = ActorSystem("PiClient", ConfigFactory.load.getConfig("remotelookup"))
    val master = system.actorFor("akka://PiServer@127.0.0.1:2552/user/master")    
     
    // start the calculation
    master ! Calculate
  }
}        */
