import sbt.Keys._

lazy val commonSettings = Seq(
  name := "scala-phantom-io",
  version := "0.1",
  scalaVersion := "2.12.6",
  scalacOptions := Seq(
    "-deprecation",
    "-feature"
  )
)

lazy val core = Core.project

lazy val root = Project(
  "scala-phantom-io",
  file(".")
).dependsOn(
  core
).aggregate(
  core
).settings(
  commonSettings
)
