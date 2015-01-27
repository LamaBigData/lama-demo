package com.htc.studio.example.wordcount

import com.twitter.scalding.Args
import com.twitter.summingbird.Platform
import com.twitter.summingbird.batch.Batcher

import com.htc.studio.storehaus.valuewapper.Column._
import com.htc.studio.summingbird.job.HTCJob
import com.htc.studio.util.jdbc._

/**
 * A demo abstract job for counting words. The output is always jdbc so as to
 * visualize the results.
 *
 * Notice that "P" remains abstract here could be replaced by a platform
 * that HTCJob supports.
 *
 * @author Sean Guo (sean_guo@htc.com)
 *
 * @tparam P The execute Platform of the job.
 */
trait WordCountJob[P <: Platform[P]] extends HTCJob[P] {
  /** The command line argument. */
  val args: Args

  /**
   * Batcher here is used to declare how often this job will run.
   * Batcher.ofDays(1) means this job will run once every day. For
   * streaming job, it is only used for merging.
   */
  override implicit val batcher = Batcher.ofDays(1)

  /** Declare source of the job. You can declare multiple sources. */
  val source: Source[String]

  /** Declare store of the job. */
  val store: Store[String, Long]

  /** Database connection url. Default is h2 tcp at localhost. */
  val connectionURL = args.getOrElse("connection",
    "h2:tcp://sa:@localhost//tmp/demo/h2")
  /** Database table name. Default is `wordcount`. */
  val tableName = args.getOrElse("table", "wordcount")

  /** The jdbc connection. */
  lazy val connection = JDBCConnection(connectionURL)
  /** The jdbc table. Columns are: word and count. */
  lazy val table = JDBCTable(tableName,
    Seq(stringJDBCKey("word")), Seq(int64("count")))

  /**
   * Split a sentence to a words array. This function only considers A-Z, a-z,
   * 0-9 and _ as word characters. Besides split, it also convert all words into
   * lower case and remove empty words or those start with a number.
   * @param sentence The input sentence, which is the record type of the source.
   * @return the list of all words in the sentence, may contain duplicate
   * words.
   */
  def toWords(sentence: String): Array[String] = sentence.toLowerCase
    .replaceAll("[^a-zA-Z0-9\\s]", " ")
    .split("\\s+")
    .filter(s => s != "" && !s(0).isDigit)

  /**
   * The actual job logic. Notice that you don't need to specify platform here.
   * This job will work in any supported platforms.
   * In this job logic, each sentence from source is splitted to a group of
   * words with method [[toWords]], then each word is tupled with a "1", which
   * indicates this word has appeared once.
   * In sumByKey, the value "1" of the same word (key) will be added together,
   * and we will have (word, count) tuples.
   *
   * @return This summer is used by Summingbird for planning the job.
   */
  override def job = source
    .flatMap { sentence => toWords(sentence).map(_ -> 1L) }
    .sumByKey(store)
}
