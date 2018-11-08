package nju.software.graph.util

import java.text.SimpleDateFormat
import java.util.Date

object Util {
  def main(args: Array[String]): Unit = {
    val tm:Long = 1361014306
    val a = tranTimeToString(tm)
    println(a)

    val tm2: String = "2013-08"
    println(tranTimeToLong(tm2))
  }
  def tranTimeToString(tm: Long) :String={
    val fm = new SimpleDateFormat("yyyy-MM")
    val tim = fm.format(new Date(tm * 1000))
    tim
  }
  def tranTimeToLong(tm:String) :Long={
    val fm = new SimpleDateFormat("yyyy-MM")
    val dt = fm.parse(tm)
    val aa = fm.format(dt)
    val tim: Long = dt.getTime()
    tim
  }
}
