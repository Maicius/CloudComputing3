package nju.software.graph.main

import scala.io.Source
import org.apache.spark._
import org.apache.spark.graphx.{Graph, VertexId}
import org.apache.spark.graphx.lib.ShortestPaths
import org.apache.spark.rdd.RDD
import java.io.PrintWriter
import java.io.File
import scala.collection.mutable.ArrayBuffer

/**
  * Hello world!
  *
  */

object Main {
  def main(args: Array[String]): Unit = {
    val file_path = args(0)
    val conf = new SparkConf().setAppName("GraphX").setMaster("spark://192.168.68.11:7077")
    val sc = new SparkContext(conf)
    val id_data = read_variables(file_path)
    val edge_data: RDD[(VertexId, VertexId)] = sc.textFile(file_path + "data.txt").map { line =>
      val data = line.split(",")
      (data(0).substring(2).toLong, data(1).substring(2).toInt)
    }
    val graph = Graph.fromEdgeTuples(edge_data, 1)
    val raw_graph = Graph.fromEdgeTuples(edge_data, 1)
    // 计算重复边
    val repeat_edge = graph.edges.map(x => (x, 1)).reduceByKey((a, b) => a + b)
    val repeat_length = repeat_edge.collect().count(e => e._2 >= 2)
    println("重复边数量：", repeat_length)
    val double_edge = (graph.reverse.edges.collect().toSet & graph.edges.collect().toSet).toList.length / 2
    println("双向边数量:", double_edge)
    val results = ShortestPaths.run(graph, Seq(id_data._1, id_data._2)).vertices.filter(x => x._1 == id_data._1).collect.map {
      x => x._2.getOrElse(id_data._2, 0)
    }.toList
    println("最短路径：", results)
    val result = ArrayBuffer(double_edge, repeat_length, results.head)
    val writer = new PrintWriter(new File(file_path + "MG1832011.txt"))
    for (i <- 0 to 2)
      writer.println(result(i))
    writer.close()
  }
  def read_variables(file_path: String): (Int, Int) = {
    val file = Source.fromFile(file_path + "variables.txt")
    val id_data: ArrayBuffer[String] = new ArrayBuffer[String]()
    for (line <- file.getLines()) {
      id_data += line
    }
    (id_data(0).substring(2).toInt, id_data(1).substring(2).toInt)
  }

}
