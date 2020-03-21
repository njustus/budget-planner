name := """budget-planner"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  guice,
  "org.postgresql" % "postgresql" % "42.2.+",
  "com.typesafe.play" %% "play-slick" % "4.0.+",
  "com.dripower" %% "play-circe" % "2812.+",
  "io.circe" %% "circe-core" % "0.13.+",
  "io.circe" %% "circe-generic" % "0.13.+",
  "org.typelevel" %% "cats-core" % "2.0.+",
  "io.scalaland" %% "chimney" % "0.5.+",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
