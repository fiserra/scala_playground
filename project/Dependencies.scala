import sbt._
import Keys._

object Dependencies {
  val cats_dep = "org.typelevel" %% "cats" % "0.7.2"
  val monix = "io.monix" %% "monix" % "2.0.0"
  val monix_cats = "io.monix" %% "monix-cats" % "2.0.0"
}