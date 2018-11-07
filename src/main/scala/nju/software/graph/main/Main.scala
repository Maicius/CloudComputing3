package nju.software.graph.main
import org.apache.spark._
import org.apache.spark.graphx.{Edge, Graph, VertexId}
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD

import scala.collection.mutable.ArrayBuffer
/**
 * Hello world!
 *
 */

object Main {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("GraphX").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val data = sc.textFile("maicius_source_targets.csv")
    val array = data.map(x => x.split(","))

    print(array)
  }

}
