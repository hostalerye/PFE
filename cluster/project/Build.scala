import sbt._
import Keys._
import AndroidKeys._

object General {
  val settings = Defaults.defaultSettings ++ Seq (
    name := "Cluster",
    version := "0.1",
    versionCode := 0,
    scalaVersion := "2.10.0",
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
      resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
    )
}

object AndroidBuild extends Build {

  lazy val main = Project (
    "Cluster",
    file("."),
    settings = General.fullAndroidSettings
  )
}
