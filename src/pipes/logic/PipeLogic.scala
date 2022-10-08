package pipes.logic

import engine.helpers.Point
import pipes.logic._

class PipeLogic() {
  var score = 0
  var required = 7

  val rand = new scala.util.Random
  var gameState = new GameState(generateFirstUpcoming(), makeEmptyBoard())

  def getCellType(point: Point): Cell = gameState.board(point.y)(point.x)

  def mouseClick(point: Point): Unit = {
    if (isBoardSpaceEmpty(point)) placeCell(point, popUpcoming())
  }


  def advanceTime(): Unit = {

  }

  def getUpcoming(): List[Cell] = gameState.upcoming

  def popUpcoming(): Cell = {
    val temp = gameState.upcoming.last
    changeUpcoming(gameState.upcoming.indices.foldLeft(List[Cell]()) { (accumulator, x) =>
      if (accumulator.isEmpty) accumulator.appended(generateNewCell()) else accumulator.appended(gameState.upcoming(x - 1))
    })
    temp
  }

  def generateFirstUpcoming(): List[Cell] = List(generateNewCell(), generateNewCell(), generateNewCell(), generateNewCell(), generateNewCell())

  def changeUpcoming(upcoming: List[Cell]): Unit = gameState = gameState.copy(upcoming = upcoming)

  def changeBoard(board: Seq[Seq[Cell]]): Unit = gameState = gameState.copy(board = board)

  def placeCell(point: Point, cellToPlace: Cell): Unit = {
    changeBoard(gameState.board.zipWithIndex.map(row => row._1.zipWithIndex.map(item =>
     if (row._2 == point.y && item._2 == point.x) cellToPlace else item._1
    )))
  }

  def generateNewCell(): Cell = rand.nextInt(7) match {
    case 0 => PlusCell()
    case 1 => DashCell()
    case 2 => ICell()
    case 3 => NECell()
    case 4 => NWCell()
    case 5 => SECell()
    case 6 => SWCell()
  }

  def generateStartCell(): Cell = rand.nextInt(4) match {
    case 0 => SourceNCell()
    case 1 => SourceSCell()
    case 2 => SourceECell()
    case 3 => SourceWCell()
  }

  def makeEmptyBoard(): Seq[Seq[Cell]] = {
    val emptyRow = Seq.fill(10)(Empty())
    Seq.fill(7)(emptyRow)
  }

  def isBoardSpaceEmpty(point: Point): Boolean = gameState.board(point.y)(point.x) == Empty()
}

case class GameState(val upcoming: List[Cell], val board: Seq[Seq[Cell]]) {}

object PipeLogic {
  val FramesPerSecond: Int = 5 // change this to speed up or slow down the game

  def apply() = new PipeLogic()
}