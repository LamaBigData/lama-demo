package com.htc.studio.example.sentiment

import java.util.Properties
import java.util.logging.{Level, Logger}

import scala.collection.JavaConverters._
import scala.util.{Random, Try}

import com.twitter.scalding.Args
import com.twitter.summingbird.batch.Batcher

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations
import edu.stanford.nlp.pipeline.StanfordCoreNLP
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.AnnotatedTree

import org.apache.tika.language.LanguageIdentifier

import com.htc.studio.storehaus.valuewapper.Column._
import com.htc.studio.summingbird.storm.HTCStormJob
import com.htc.studio.util.jdbc._

object SentimentJob {
  /** Try to use real model or fake with a random sentiment. */
  val sentiment: String => Option[Int] = Try {
    // disable parser warnings
    Logger.getLogger("edu.stanford.nlp.process.PTBLexer").setLevel(Level.OFF)

    /** Properties to create a stanford nlp pipeline. */
    val props = new Properties
    props.setProperty("annotators", "tokenize, ssplit, parse, sentiment")
    /** The stanford nlp pipeline. This will fail is fakeModel is true. */
    val pipeline = new StanfordCoreNLP(props)

    // use real model to generate sentiment score
    text: String => pipeline.process(text)
      .get(classOf[SentencesAnnotation]).asScala
      .map { sentence =>
        val tree = sentence.get(classOf[AnnotatedTree])
        (sentence.toString.size, RNNCoreAnnotations.getPredictedClass(tree))
      }
      // determine by sentence with max length
      .reduceOption(Ordering.by((_: (Int, Int))._1).min)
      .map(_._2)
  }.toOption
    // fall back to random sentiment score
    .getOrElse { text: String => Some(Random.nextInt(3) + 1) }
}

/**
 * A storm job that calculates tweet sentiment. Only English tweets are used.
 *
 * @author Zhongyang Zheng (zhongyang_zheng@htc.com)
 */
case class SentimentJob(args: Args) extends HTCStormJob {
  override val batcher = Batcher.ofDays(1)

  /** Twitter4j Properties. */
  val props = new Properties
  args.optional("twitter4j").foreach { file =>
    val source = scala.io.Source.fromFile(file)
    val reader = source.bufferedReader
    props.load(reader)
    reader.close
    source.close
  }

  /** Database connection url. Default is h2 tcp at localhost. */
  val connectionURL = args.getOrElse("connection",
    "h2:tcp://sa:@localhost//tmp/demo/h2")
  /** Database table name. Default is `sentiment`. */
  val tableName = args.getOrElse("table", "sentiment")

  /** The jdbc connection. */
  val connection = JDBCConnection(connectionURL)
  /** The jdbc table. Columns are: tweet id, tweet text and sentiment score. */
  val table = JDBCTable(tableName, Seq(int64("id")),
    Seq(string("tweet"), int32("sentiment")))

  /** Twitter4j source. */
  val source = Source.twitter(props)
  /** JDBC sink. */
  val sink = Storehaus.jdbc[Long, (String, Int)](connection, table).fixedSink

  override def job = source
    // only use english tweets
    .filter(tweet => new LanguageIdentifier(tweet.getText).getLanguage == "en")
    .optionMap(tweet => SentimentJob.sentiment(tweet.getText).map(s =>
      (tweet.getId, (tweet.getText, s))))
    .write(sink)
}
