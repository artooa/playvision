
name := "playvision"

version in Global := "0.0.1"

name := "playvision"

organization in Global := "artooa"

organizationName in Global := "Artooa GmbH"

organizationHomepage in Global := Some(url("http://artooa.ch"))

homepage in Global := Some(url("http://artooa.ch"))

lazy val root = (project in file(".")).enablePlugins(PlayScala).settings(
      sourceGenerators in Compile <+= (sourceManaged in Compile).map {
      case sourcesDirectory =>
        val generated = sourcesDirectory / "Infos.scala"
        IO.write(generated, """
            package models {
              object Infos { val GIT_TAG = "%s" }
            }""".format(Process("git describe --tags").lines.head))  //" git rev-parse HEAD" / git describe --always
        Seq(generated)
      }
    )

scalaVersion in Global := "2.11.8"

libraryDependencies ++= Seq(
  "org.webjars" 		%% 	"webjars-play" 		% "2.3.0",
  "org.webjars" % "bootstrap" % "3.1.1",
  "org.webjars" % "jquery" % "2.1.0-2",
  //"org.webjars" % "requirejs" % "2.1.11-1",
  "com.typesafe.play.plugins" %% "play-plugins-mailer" % "2.3.0",
  "org.webjars" % "bootstrap" % "3.1.1-2"
  // Test dependencies
  //"org.webjars" % "rjs" % "2.1.11-1-trireme" % "test",
  //"org.webjars" % "squirejs" % "0.1.0" % "test"
)

libraryDependencies ++= Seq(
  ws,
  cache
)

// JsEngineKeys.engineType := JsEngineKeys.EngineType.Node

MochaKeys.requires += "./Setup"

// Apply RequireJS optimization, digest calculation and gzip compression to assets
pipelineStages := Seq(digest, gzip) //Seq(rjs, digest, gzip)

scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation")

useJGit
