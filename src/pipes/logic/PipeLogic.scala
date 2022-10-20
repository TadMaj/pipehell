package pipes.logic

import engine.helpers.Point

class PipeLogic(val level: Int, val startingScore: Int) {
  val rand = new scala.util.Random

  val flowTime: Double = 60 - math.pow(0.9, level.toDouble)
  var currentFlowTime: Int = flowTime.toInt
  var startTime = 60

  val startPoint: Point = Point(rand.nextInt(7) + 1, rand.nextInt(5) + 1)

  var score: Int = startingScore
  val required: Int = 7 + level
  var requiredCurr = 0

  var gameState: GameState = GameState(generateFirstUpcoming(), makeStartingBoard(startPoint), startPoint)

  var gameOver = false

  def getCellType(point: Point): Cell = gameState.board(point.y)(point.x)

  def mouseClick(point: Point): Unit = if (isBoardSpaceEmpty(point)) placeCell(point, popUpcoming())

  def getUpcoming: List[Cell] = gameState.upcoming

  def advanceTime(): Unit = {
    if (startTime != 0) {
      startTime -= 1
      return
    }

    if (currentFlowTime != 0) {
      currentFlowTime -= 1
      setFillOnActivePipe()
      return
    }

    currentFlowTime = flowTime.toInt
    changeActivePipe(getNewActivePipe)
  }

  def goalReached(): Boolean = requiredCurr >= required

  private def checkBounds(point: Point): Boolean = point.x < 0 || point.y < 0 || point.x > 9 || point.y > 6

  private def generateNewCell(): Cell = rand.nextInt(7) match {
    case 0 => PlusCell()
    case 1 => DashCell()
    case 2 => ICell()
    case 3 => NECell()
    case 4 => NWCell()
    case 5 => SECell()
    case 6 => SWCell()
  }

  private def generateStartCell(): Cell = rand.nextInt(4) match {
    case 0 => SourceNCell()
    case 1 => SourceSCell()
    case 2 => SourceECell()
    case 3 => SourceWCell()
  }

  private def currentPipe: Cell = gameState.board(gameState.activePipe.y)(gameState.activePipe.x)

  private def setFillOnActivePipe(): Unit = currentPipe.filled = 1-currentFlowTime.toFloat/flowTime.toFloat

  private def changeUpcoming(upcoming: List[Cell]): Unit = gameState = gameState.copy(upcoming = upcoming)

  private def changeBoard(board: Seq[Seq[Cell]]): Unit = gameState = gameState.copy(board = board)

  private def isBoardSpaceEmpty(point: Point): Boolean = gameState.board(point.y)(point.x) == Empty()

  private def makeEmptyBoard(): Seq[Seq[Cell]] = Seq.fill(7)(Seq.fill(10)(Empty()))

  private def makeStartingBoard(point: Point): Seq[Seq[Cell]] = {
    makeEmptyBoard().zipWithIndex.map(x => if (point.y == x._2) x._1.updated(point.x, generateStartCell()) else x._1)
  }

  private def generateFirstUpcoming(): List[Cell] = List(generateNewCell(), generateNewCell(), generateNewCell(), generateNewCell(), generateNewCell())

  private def placeCell(point: Point, cellToPlace: Cell): Unit = changeBoard(gameState.board.zipWithIndex.map(x =>
    if (x._2 == point.y) x._1.updated(point.x, cellToPlace) else x._1))

  private def directionFromPoints(first: Point, second: Point): Direction = first - second match {
    case Point(0, -1) => North()
    case Point(0, 1) => South()
    case Point(-1, 0) => West()
    case Point(1, 0) => East()
    case _ => North()
  }

  private def addScore(): Unit = {
    score += 100 * (level + 1)
    requiredCurr += 1
  }

  private def popUpcoming(): Cell = {
    val temp = gameState.upcoming.last
    changeUpcoming(List(generateNewCell()) ++ gameState.upcoming.dropRight(1))
    temp
  }

  private def getNewActivePipe: Point = {
    currentPipe match {
      case o: Source => gameState.activePipe + o.connections.head.relativeToPoint()
      case o: Cell => gameState.activePipe + o.getConnection.relativeToPoint()
      case _ => Point(-1, -1)
    }
  }

  private def checkGameOver(point: Point): Boolean = {
    if (getCellType(point) == Empty() || checkBounds(point) || !getCellType(point).connections.contains(directionFromPoints(gameState.activePipe, point))) gameOver = true
    gameOver
  }

  private def changeActivePipe(point: Point): Unit = {
    if (checkGameOver(point)) return
    addScore()
    getCellType(point).sourcePipe = Some(directionFromPoints(gameState.activePipe, point))
    gameState = gameState.copy(activePipe = point)
  }
}

case class GameState(upcoming: List[Cell], board: Seq[Seq[Cell]], activePipe: Point) {}

object PipeLogic {
  val FramesPerSecond: Int = 5

  def apply(level: Int, score: Int) = new PipeLogic(level, score)
}