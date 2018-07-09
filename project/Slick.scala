import sbt._
import sbt.Keys._

object Slick {

  val dependencies = Seq(
    "org.scalaz"         %% "scalaz-core" % "7.2.12",
    "org.scalatest"      %% "scalatest"   % "3.0.1"  % "test",
    "mysql"              %  "mysql-connector-java"  % "5.1.36",
    "com.typesafe.slick" %% "slick"                 % "3.2.0"
  )

  lazy val project = Project(
    "phantom-io-slick",
    file("phantom-io-slick")
  ).settings(
    libraryDependencies ++= dependencies,
    scalaSource in Compile := baseDirectory.value / "src" / "main" / "scala",
    scalaSource in Test := baseDirectory.value / "src" / "test" / "scala",
    resourceDirectory in Compile := baseDirectory.value / "src" / "main" / "resources",
    resourceDirectory in Test := baseDirectory.value / "src" / "test" / "resources"
  ).dependsOn(
    Core.project
  )

}
