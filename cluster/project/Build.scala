import sbt._
import Keys._
import AndroidKeys._

object General {
  val settings = Defaults.defaultSettings ++ Seq (
    name := "Cluster",
    version := "0.1",
    versionCode := 0,
    scalaVersion := "2.9.2",
    platformName in Android := "android-17"
  )

  val proguardSettings = Seq (
    useProguard in Android := true
  )
  

  lazy val fullAndroidSettings =
    General.settings ++
    AndroidProject.androidSettings ++
    TypedResources.settings ++
    proguardSettings ++
    AndroidManifestGenerator.settings ++
    AndroidMarketPublish.settings ++ Seq (
      keyalias in Android := "change-me",
      resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
      libraryDependencies += "org.scalatest" %% "scalatest" % "1.8" % "test",
      libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0.5",
      libraryDependencies += "com.typesafe.akka" % "akka-remote" % "2.0.5"
    )
}

object AndroidBuild extends Build {

  lazy val main = Project (
    "Cluster",
    file("."),
    settings = General.fullAndroidSettings
  )

  lazy val tests = Project (
    "tests",
    file("tests"),
    settings = General.settings ++
               AndroidTest.androidSettings ++
               General.proguardSettings ++ Seq (
      name := "ClusterTests"
    )
  ) dependsOn main
}
