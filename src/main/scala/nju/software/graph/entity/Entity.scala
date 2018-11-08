package nju.software.graph.entity

import scala.collection.mutable.ArrayBuffer

object Entity {

  case class DateResult(date: String, friend_list: ArrayBuffer[(String, Int, String, Int)])

}
