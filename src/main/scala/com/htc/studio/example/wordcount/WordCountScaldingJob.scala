package com.htc.studio.example.wordcount

import scala.util.Try

import com.twitter.bijection.Injection
import com.twitter.scalding.Args
import com.twitter.summingbird.scalding.Scalding

import com.htc.studio.summingbird.scalding.HTCScaldingJob

/**
 * The example job running on scalding platform. Job logic has been defined at
 * WordCountJob. This job specifies the WordCountJob to run on a Scalding
 * platform as a batch job.
 *
 * @note This just servers as a code example, but does not compile (due to lack
 * of scalding jars).
 */
class WordCountScaldingJob(override val args: Args)
  extends WordCountJob[Scalding] with HTCScaldingJob {
  /**
   * Get input path for the batch job from argument. When you run this job, you
   * can pass the input path by adding
   * " --input-path <PATH TO INPUT DIRECTORY OF DATA>" command line arguments.
   * The default input is the README.md file.
   */
  val inputPath = args.getOrElse("input-path", "README.md")

  /**
   * Specify type of source for the job. This source is a text source, which
   * means input data will be read as text lines.
   */
  override val source = Source.text(inputPath)

  /** Define store for job result. */
  override val store = Store.jdbc[String, Long, (String, Long)](connection, table)
}
