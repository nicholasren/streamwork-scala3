import sbt.Keys.libraryDependencies

val scala3Version = "3.1.3"
val zioVersion = "2.0.0"

lazy val commonSettings = Seq(
  version := "0.1.0",
  scalaVersion := scala3Version,
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.12" % "test",
  libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.36",
  libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.36"
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

lazy val example = project
  .in(file("example"))
  .settings(
    name := "example",
    commonSettings
  ).dependsOn(engine)