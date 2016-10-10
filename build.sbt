name := "scala_playground"
import Dependencies._

lazy val commonSettings = Seq(
  organization := "com.fiser",
  version := "0.1.0",
  scalaVersion := "2.11.8"
)

lazy val fp = (project in file("fp")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies ++= Seq(cats_dep, monix, monix_cats)
  )

lazy val cats = (project in file("cats")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies ++= Seq(cats_dep)
  )

lazy val type_classes = (project in file("type_classes")).
  settings(commonSettings: _*).
  settings(
    // other settings
  )
    