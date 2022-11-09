import scala.collection.mutable
import scala.util.{Failure, Success, Try}

object Main {

  var board: List[List[Int]] = List(
    List(0,0,0,0,0),
    List(0,0,1,0,0),
    List(0,0,1,0,0),
    List(0,0,1,0,0),
    List(0,0,0,0,0),
  )

  def main(args: Array[String]): Unit = {

    val hashMapList = board.zipWithIndex.map{ pair =>
      val rowIndex = pair._2
      val columns = pair._1

      columns.zipWithIndex.map { pairColumns =>
        val columnIndex = pairColumns._2
        val columnValue = pairColumns._1
        val columnIsAlive = columnValue == 1
        val neighBoorList = List(
          (rowIndex, columnIndex - 1), //same row , previous column
          (rowIndex, columnIndex + 1), //same row , next column
          (rowIndex + 1, columnIndex), //next row , same column
          (rowIndex + 1, columnIndex + 1), //next row , next column
          (rowIndex + 1, columnIndex - 1),//next row , previous column
          (rowIndex - 1, columnIndex - 1),//previous row , previous column
          (rowIndex - 1, columnIndex + 1), //previous row , next column
          (rowIndex - 1, columnIndex), //previous row , same column
        )

        val countNeighbors = neighBoorList.count(item => {
          Try(board(item._1)(item._2)) match {
            case Success(value) => value == 1
            case Failure(exception) => false
          }
        })

        val newValue = countNeighbors match {
          case value if value < 2 && columnIsAlive => 0
          case value if (value == 2 || value == 3) && columnIsAlive  => 1
          case value if value > 3 && columnIsAlive  => 0
          case value if value == 3 && !columnIsAlive  => 1
          case _ => 0
        }
        newValue
      }
    }

    hashMapList.map(println)
  }
}