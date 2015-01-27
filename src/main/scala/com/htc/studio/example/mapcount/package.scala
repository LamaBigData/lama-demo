package com.htc.studio.example

import scala.language.implicitConversions

import com.twitter.algebird.{HLL, HyperLogLogMonoid, SketchMap, SketchMapParams}

package object mapcount {

  val Tab = "\t"
  val Colon = ":"
  val corekinds = 50
  // params for HyperLogLog Monoid
  val Bits = 12
  // params for SketchMap Monoid
  val Delta = 1E-8
  val Eps = 0.001
  val SmSeed = 1
  val HeavyHittersCount = 10
  implicit def toBytes(v: String): Array[Byte] = v.getBytes
  val SmParams = SketchMapParams[String](SmSeed, Eps, Delta, HeavyHittersCount)

  /**
   * Device Log Object. contains:
   *  - logging time stamp
   *  - device type info
   *  - country name info
   */
  case class DeviceLogObj(
    val timeStamp: Long,
    val deviceId: String,
    val region: String
  )

  type vType = (Long, HLL, SketchMap[String, Long])
  type storeType = (Long, Long, String, String, String)
}
