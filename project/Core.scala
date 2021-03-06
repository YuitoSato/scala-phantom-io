import sbt._
import sbt.Keys._

object Core {

  val dependencies = Seq(
    "org.scalaz"    %% "scalaz-core" % "7.2.12",
    "org.scalatest" %% "scalatest"   % "3.0.1"  % "test"
  )

  lazy val project = Project(
    "phantom-io-core",
    file("phantom-io-core")
  ).settings(
    libraryDependencies ++= dependencies,
    scalaSource in Compile := baseDirectory.value / "src" / "main" / "scala",
    scalaSource in Test := baseDirectory.value / "src" / "test" / "scala",
    resourceDirectory in Compile := baseDirectory.value / "src" / "main" / "resources",
    resourceDirectory in Test := baseDirectory.value / "src" / "test" / "resources"
  )

}
