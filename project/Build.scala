import sbt._
import Keys._
import play._
import play.Play.autoImport._

object LamaDemo extends Build {
  val fakeModel = SettingKey[Boolean]("fake-model", "Use fake model for sentiment analysis")

  lazy val demo = (project in file(".")).settings(
    fork := true,
    scalaVersion := "2.10.4",
    resolvers ++= Seq(
      Resolver.file("lama", file("lib"))(Resolver.ivyStylePatterns),
      "clojars" at "http://clojars.org/repo",
      "conjars" at "http://conjars.org/repo",
      "twitter" at "http://maven.twttr.com"
    ),
    libraryDependencies ++= Seq(
      "com.htc.lama" %% "lama-storm" % "0.0.2",
      "com.h2database" % "h2" % "1.4.182",
      "edu.stanford.nlp" % "stanford-corenlp" % "3.4.1",
      "org.apache.tika" % "tika-core" % "1.6"
    ),
    sources in Compile ~= { s: Seq[File] => s.filter(_.getName != "WordCountScaldingJob.scala") },
    fakeModel := true,
    libraryDependencies ++= (fakeModel.value match {
      case true => Nil
      case false => Seq("edu.stanford.nlp" % "stanford-corenlp" % "3.4.1" classifier "models")
    })
  )

  lazy val play = project.enablePlugins(PlayScala).settings(
    libraryDependencies ++= Seq(
      "com.h2database" % "h2" % "1.4.182",
      "net.contentobjects.jnotify" % "jnotify" % "0.94",
      "org.webjars" %% "webjars-play" % "2.3.0",
      // Web jars libraries, will be extracted to lib/ directory
      "org.webjars" % "coffee-script" % "1.7.1",
      "org.webjars" % "d3-cloud" % "1.0.5",
      "org.webjars" % "d3js" % "3.5.2",
      "org.webjars" % "jquery" % "1.11.1",
      // existing play modules
      anorm,
      jdbc
   )
  )
}
