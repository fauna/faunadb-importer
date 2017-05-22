name := "faunadb-importer"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings")

// TODO: Check licences
libraryDependencies ++= Seq(
  // Main
  "com.faunadb" %% "faunadb-scala" % "1.1.0",
  "com.typesafe.akka" %% "akka-actor" % "2.5.0",
  "com.typesafe.akka" %% "akka-stream" % "2.5.0",
  "com.github.scopt" %% "scopt" % "3.5.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.8.6",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.4",
  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-csv" % "2.6.4",
  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % "2.6.4",

  // Test
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

// TODO: create and package a ./bin/import script with sensible java options
