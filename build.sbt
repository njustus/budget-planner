
lazy val root = (project in file("."))
.enablePlugins(PlayScala)
.enablePlugins(FlywayPlugin)
.settings(Seq(
  sources in (Compile, doc) := Seq.empty,
  publishArtifact in (Compile, packageDoc) := false,
  PlayKeys.playDefaultPort := 8080,
  name := """budget-planner""",
  organization := "com.example",
  version := "1.0-SNAPSHOT",
  scalaVersion := "2.13.1",
  flywayLocations := Seq("filesystem:migrations"),
  flywayUrl := "jdbc:postgresql://localhost:5432/budget-planner",
  flywayUser := "planner",
  flywayPassword := "testPass",
))

scalacOptions += "-Ymacro-annotations"

libraryDependencies ++= Seq(
  guice,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.20.3-play28",
  "com.dripower" %% "play-circe" % "2812.+",
  "io.circe" %% "circe-core" % "0.13.+",
  "io.circe" %% "circe-generic" % "0.13.+",
  "io.circe" %% "circe-generic-extras" % "0.13.+",
  "org.typelevel" %% "cats-core" % "2.0.+",
  "io.scalaland" %% "chimney" % "0.5.+",
  "com.pauldijou" %% "jwt-circe" % "4.2.+",
  "com.github.javafaker" % "javafaker" % "0.+",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
