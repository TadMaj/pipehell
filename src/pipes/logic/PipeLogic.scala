package pipes.logic

import engine.helpers.Point

class PipeLogic() {
  val rand = new scala.util.Random

  val flowTime = 60
  var currentFlowTime: Int = flowTime
  var startTime = 60

  val startPoint: Point = Point(rand.nextInt(7) + 1, rand.nextInt(5) + 1)

  var score = 0
  val required = 7

  var gameState: GameState = GameState(generateFirstUpcoming(), makeStartingBoard(startPoint), startPoint)

  def getCellType(point: Point): Cell = gameState.board(point.y)(point.x)

  def mouseClick(point: Point): Unit = if (isBoardSpaceEmpty(point)) placeCell(point, popUpcoming())

  def advanceTime(): Unit = {
    if (startTime != 0) {
      startTime -= 1
      return
    }

    if (flowTime != 0) {
      currentFlowTime -= 1
      return
    }

    currentFlowTime = flowTime
    changeActivePipe(getActivePipe)
  }

  def getActivePipe: Point = {
    Point(0, 0)
  }

  def changeActivePipe(point: Point): Unit = gameState = gameState.copy(activePipe = point)

  def getUpcoming: List[Cell] = gameState.upcoming

  def popUpcoming(): Cell = {
    val temp = gameState.upcoming.last
    changeUpcoming(List(generateNewCell()) ++ gameState.upcoming.dropRight(1))
    temp
  }

  def generateFirstUpcoming(): List[Cell] = List(generateNewCell(), generateNewCell(), generateNewCell(), generateNewCell(), generateNewCell())

  def changeUpcoming(upcoming: List[Cell]): Unit = gameState = gameState.copy(upcoming = upcoming)

  def changeBoard(board: Seq[Seq[Cell]]): Unit = gameState = gameState.copy(board = board)

  def placeCell(point: Point, cellToPlace: Cell): Unit = changeBoard(gameState.board.zipWithIndex.map(x =>
    if (x._2 == point.y) x._1.updated(point.x, cellToPlace) else x._1))

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

  def makeStartingBoard(point: Point): Seq[Seq[Cell]] = {
    makeEmptyBoard().zipWithIndex.map(x => if (point.y == x._2) x._1.updated(point.x, generateStartCell()) else x._1)
  }

  def isBoardSpaceEmpty(point: Point): Boolean = gameState.board(point.y)(point.x) == Empty()
}

case class GameState(upcoming: List[Cell], board: Seq[Seq[Cell]], activePipe: Point) {}

object PipeLogic {
  val FramesPerSecond: Int = 5

  def apply() = new PipeLogic()
}