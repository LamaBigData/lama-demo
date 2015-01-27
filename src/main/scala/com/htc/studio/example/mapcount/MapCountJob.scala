package com.htc.studio.example.mapcount

import com.twitter.algebird.{HyperLogLogMonoid, SketchMapMonoid}
import com.twitter.summingbird.Platform
import com.twitter.summingbird.batch.Batcher

import com.htc.studio.summingbird.job.HTCJob

/**
 * job logic define for lama job.
 * The job count handheld device popularity distribution on the whole world.
 *
 * @author Roy Jiang (roy_jiang@htc.com)
 */
trait MapCountJob[P <: Platform[P]] extends HTCJob[P] {
  override implicit val batcher = Batcher.ofHours(1)

  val source: Source[DeviceLogObj]
  val store: Store[String, vType]

  implicit val hllMonoid = new HyperLogLogMonoid(Bits)
  implicit val smMonoid  = new SketchMapMonoid[String, Long](SmParams)

  /**
   * The actual Summingbird job. Notice that the execution platform
   * "P" stays abstract. This job will work just as well in memory,
   * in Streaming or Batch model.
   *
   * The Job will count
   *  - handheld device usage frequency for each country
   *  - number of different devices type for each country
   *  - three most popular mobile devices for each country
   */
  override def job = source
    .map { log => log.region -> (
       1L,
       hllMonoid.create(log.deviceId),
       smMonoid.create(log.deviceId, 1L)) }
    .sumByKey(store)
}
