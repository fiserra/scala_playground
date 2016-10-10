import scala.io.StdIn

object Solution {

  def main(args: Array[String]) {
    StdIn.readLine()

    val numbersString = StdIn.readLine()

    val result = numbersString.split(" ").map(_.toInt).foldLeft(Map[Int, Int]()) {
      (map, i) =>
        map + (i -> (map.getOrElse(i, 0) + 1))
    }.filter(_._2 == 1).keySet.mkString(" ")

    println(result)
  }
}