// Comment to get more information during initialization
logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Typesafe repository mwn" at "http://repo.typesafe.com/typesafe/maven-releases/"

// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.10")

// web plugins

//addSbtPlugin("com.typesafe.sbt" % "sbt-coffeescript" % "1.0.0")

//addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.0.0")

//addSbtPlugin("com.typesafe.sbt" % "sbt-jshint" % "1.0.0")

//addSbtPlugin("com.typesafe.sbt" % "sbt-rjs" % "1.0.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.0.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-mocha" % "1.0.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.6.4")

addSbtPlugin("com.geirsson" % "sbt-scalafmt" % "0.3.0-RC1")