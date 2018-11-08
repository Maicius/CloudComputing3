package nju.software.graph.accumulator

import nju.software.graph.entity.Entity.DateResult
import org.apache.spark.util.AccumulatorV2

import scala.collection.mutable

/**
  * 保存日期结果的累加器
  */
class DateResultAccumulator extends AccumulatorV2[DateResult, mutable.Map[String, DateResult]] {
  val resultMap: mutable.Map[String, DateResult] = mutable.Map()

  override def isZero: Boolean = resultMap.isEmpty

  override def copy(): AccumulatorV2[DateResult, mutable.Map[String, DateResult]] = DateResultAccumulator.this

  override def reset(): Unit = resultMap.clear()

  override def add(v: DateResult): Unit = {
    resultMap += (v.date -> v)
  }

  override def merge(other: AccumulatorV2[DateResult, mutable.Map[String, DateResult]]): Unit = {
    this.value ++= other.value
  }

  override def value: mutable.Map[String, DateResult] = resultMap

}
