import sbt.Keys.libraryDependencies

val scala3Version = "3.1.2"
lazy val commonSettings = Seq(
  version := "0.1.0",
  scalaVersion := scala3Version,
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.12" % "test"
)

lazy val root = project
  .in(file("."))
  .settings(
    name := "streamwork",
    commonSettings
  )

lazy val engine = project
  .in(file("engine"))
  .settings(
    name := "engine",
    commonSettings,
    libraryDependencies += "io.javalin" % "javalin" % "4.6.3",
  )
