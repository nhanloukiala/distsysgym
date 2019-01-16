name := "hacks"

version := "0.1"

scalaVersion := "2.12.4"

scalacOptions += "-Ypartial-unification"
lazy val akkaVersion = "2.5.3"

val circeVersion = "0.10.0"

val circe = Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

val akka = Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.13",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.13" % Test,
  "org.typelevel" %% "cats-core" % "1.0.1",
  "com.typesafe" % "config" % "1.3.2",
  "com.google.guava" % "guava" % "27.0-jre"
) ++ circe ++ akka