import sbt._
import sbt.Keys._

object PhantomTransaction {

  val dependencies = Seq(
    "org.scalaz"    %% "scalaz-core" % "7.2.12",
    "org.scalatest" %% "scalatest"   % "3.0.1"  % "test"
  )

  lazy val project = Project(
    "phantom-transaction",
    file("phantom-transaction")
  ).settings(
    scalaSource in Compile := baseDirectory.value / "src" / "main" / "scala",
    scalaSource in Test := baseDirectory.value / "src" / "test" / "scala",
    resourceDirectory in Compile := baseDirectory.value / "src" / "main" / "resources",
    resourceDirectory in Test := baseDirectory.value / "src" / "test" / "resources"
  ).dependsOn(
    Core.project % "test->test;compile->compile"
  )

}
