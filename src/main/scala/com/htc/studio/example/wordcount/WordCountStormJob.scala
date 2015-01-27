package com.htc.studio.example.wordcount

import scala.util.Random

import com.twitter.scalding.Args
import com.twitter.summingbird.storm.Storm

import com.htc.studio.summingbird.storm.HTCStormJob

/**
 * The demo word count job. The source is a generator that emits random words
 * every second.
 *
 * @author Zhongyang Zheng (zhongyang_zheng@htc.com)
 */
case class WordCountStormJob(override val args: Args)
  extends WordCountJob[Storm] with HTCStormJob {
  /** The word list for the generator. */
  val wordFile = args.getOrElse("words", "data/words.txt")
  /** The word list in memory. Remove those ends with 's. */
  val words = scala.io.Source.fromFile(wordFile).getLines.toList
  /** The size of word list. */
  val size = words.size

  /** The interval for the generator in milliseconds. */
  val interval = args.getOrElse("interval", "1000").toLong
  /** Max number of words per interval. */
  val maxCount = args.getOrElse("maxCount", "100").toInt

  /**
   * The generator function. Generates the same word in each interval, so that
   * the words distribution is more unbalanced in the graph.
   */
  def generator(): TraversableOnce[String] = {
    Thread.sleep(interval)
    val count = Random.nextInt(maxCount) + 1
    val value = words(Random.nextInt(size))
    Iterable.fill(count)(value)
  }

  lazy override val source = Source.generator(generator)

  lazy override val store =
    Storehaus.jdbc[String, Long](connection, table).fixedStore
}
