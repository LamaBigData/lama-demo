package com.htc.studio.example.mapcount

import javax.xml.bind.DatatypeConverter

import scala.util.{Random, Try}

import com.twitter.algebird.HyperLogLog
import com.twitter.bijection.Injection
import com.twitter.scalding.Args
import com.twitter.summingbird.storm.Storm

import com.htc.studio.storehaus.valuewapper.Column._
import com.htc.studio.summingbird.storm.HTCStormJob
import com.htc.studio.util.jdbc._

/**
 * The demo for map distribution job.
 *
 * Source is a generator that emits random device usage log every second
 *
 * Store is a h2 mem database.
 *
 * @author Roy Jiang (roy_jiang@htc.com)
 */
case class MapCountStormJob(args: Args)
  extends MapCountJob[Storm] with HTCStormJob {

  /** load all countries list into memory */
  val countries =
    scala.io.Source.fromFile("data/countries.txt").getLines.toArray
  /** The number of all countries. */
  val size = countries.size

  /** The interval for the generator in milliseconds. */
  val interval = args.getOrElse("interval", "1000").toLong
  /** Max number of words per interval. */
  val maxCount = args.getOrElse("maxCount", "200").toInt

  /**
   * The generator function. Generates device usage log in each interval,
   * so the country distribution is more unbalanced in the world map.
   */
  def generator(): TraversableOnce[DeviceLogObj] = {
    Thread.sleep(interval)
    val country = countries(Random.nextInt(size))
    (0 to Random.nextInt(maxCount)).map(_ =>
      DeviceLogObj(System.currentTimeMillis,
      "Device_" + Random.nextInt(corekinds),
      country))
  }

  lazy override val source = Source.generator(generator)

  /** Database connection url. Default is h2 tcp at localhost. */
  val connectionURL = args.getOrElse("connection",
    "h2:tcp://sa:@localhost//tmp/demo/h2")
  /** Database table name. Default is `mapcount`. */
  val tableName = args.getOrElse("table", "mapcount")

  /** The jdbc connection. */
  lazy val connection = JDBCConnection(connectionURL)
  /**
   * The jdbc table.
   * Columns are: country, total, kinds, topk3, hyperlog, sketchmap.
   */
  lazy val table = JDBCTable(tableName, Seq(stringJDBCKey("country")),
    Seq(int64("total"), int64("kinds"), stringJDBCKey("topk3"),
      stringJDBCKey("hyperlog"), stringJDBCKey("sketchmap")))

  /** ordering sequence by second value */
  implicit val ord: Ordering[(String, Long)] = Ordering[Long].on (-_._2)

  /** The injection to convert the store. */
  implicit val inj = new Injection[vType, storeType] {
    override def apply(v: vType) = {
      val seq = smMonoid.heavyHitters(v._3)
      (v._1, v._2.estimatedSize.toLong,
        seq.sorted.take(3).map(n => "%s,%s".format(n._1, n._2)).mkString("\n"),
        DatatypeConverter.printBase64Binary(HyperLogLog.toBytes(v._2)),
        seq.map(n => "%s:%s".format(n._1, n._2)).mkString("\t"))
    }

    override def invert(v: storeType) = Try {
      val seq = v._5.split(Tab)
        .map { dc =>
          val arr = dc.split(Colon)
          (arr(0), arr(1).toLong)
        }
      (v._1, HyperLogLog.fromBytes(DatatypeConverter.parseBase64Binary(v._4)),
        smMonoid.create(seq))
    }
  }

  /** store for lama demo job */
  lazy override val store = Storehaus.jdbc[String, storeType](connection, table)
    .convertValue[vType].fixedStore
}
