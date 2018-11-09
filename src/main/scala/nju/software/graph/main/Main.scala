package nju.software.graph.main

import java.io.{File, PrintWriter}

import nju.software.graph.accumulator.DateResultAccumulator
import nju.software.graph.constant.constant
import nju.software.graph.entity.Entity.DateResult
import nju.software.graph.util.Util
import org.apache.spark._
import org.apache.spark.graphx.{Edge, Graph, VertexId}
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.json4s.native.JsonMethods._
import org.json4s.JsonDSL.WithDouble._
import org.json4s.jackson.JsonMethods._

import scala.collection.mutable
import org.json4s.JsonDSL._
import org.json4s.NoTypeHints
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization._
import org.json4s.jackson.Serialization

import scala.collection.mutable.ArrayBuffer

/**
  * Hello world!
  *
  */

object Main {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("GraphX").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val dateMap = new DateResultAccumulator()
    sc.register(dateMap, "dateMap")

    val node_data: RDD[(Long, (String, Int, String))] = sc.textFile(constant.NODE_FILE_PATH).map { line =>
      val fields = line.split(",")
      (fields(0).toLong, (fields(1), fields(3).toInt, fields(4)))
    }
    val maxKey = node_data.max()(new Ordering[(Long, (String, Int, String))]() {
      override def compare(x: (Long, (String, Int, String)), y: (Long, (String, Int, String))): Int =
        Ordering[Int].compare(x._2._2, y._2._2)
    })
    println(maxKey._2._2)
    val minKey = node_data.min()(new Ordering[(Long, (String, Int, String))]() {
      override def compare(x: (Long, (String, Int, String)), y: (Long, (String, Int, String))): Int =
        Ordering[Int].compare(x._2._2, y._2._2)
    })
    println(minKey._2._2)
    val edge_data: RDD[Edge[Int]] = sc.textFile(constant.EDGE_FILE_PATH).map { line =>
      val data = line.split(" ")
      Edge(data(0).toLong, data(1).toInt)
    }
    val graph: Graph[(String, Int, String), Int] = Graph(node_data, edge_data)
    for (time <- Range(minKey._2._2, maxKey._2._2, constant.TIME_DAY)) {
      var monthData: ArrayBuffer[(String, Int, String,String, Int, String)] = new ArrayBuffer[(String, Int, String,String, Int,String)]()
      val month = Util.tranTimeToString(time)
      for (triplet <- graph.triplets.filter(t => t.dstAttr._2 > time && t.dstAttr._2 < time + constant.TIME_DAY).collect) {
        val temp_result = (triplet.srcAttr._1.toString, triplet.srcId.toInt, triplet.srcAttr._3, triplet.dstAttr._1.toString, triplet.dstId.toInt, triplet.dstAttr._3)
        monthData += temp_result
        println(s"${triplet.srcAttr._1}, ${triplet.srcId}, ${triplet.dstAttr._1}, ${triplet.dstId}")
      }
      val dateResult: DateResult = DateResult(month, monthData)
      dateMap.add(dateResult)
    }
    implicit val formats = Serialization.formats(NoTypeHints)
    println(write(dateMap.value.values.toArray))
    // val initRdd = sc.makeRDD(dateMap.value.values.toArray)
    val writer = new PrintWriter(new File("final_data2.json"))

    writer.write(write(dateMap.value.values.toArray))
    writer.close()
    // initRdd.saveAsTextFile("test.txt")
  }

}
