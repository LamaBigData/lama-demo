resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.5.0")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.7")

addSbtPlugin("com.typesafe.sbt" % "sbt-coffeescript" % "1.0.0")

dependencyOverrides += "io.spray" %%  "spray-json" % "1.2.6"
