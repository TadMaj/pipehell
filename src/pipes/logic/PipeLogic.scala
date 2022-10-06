package pipes.logic

import engine.helpers.Point

class PipeLogic() {
  var score = 0
  var required = 7
  var gameState = new GameState()

  def getCellType(point: Point): Cell = {
    Empty()
  }

  def mouseClick(point: Point): Unit = {

  }

  def advanceTime(): Unit = {

  }

  def getUpcoming(): List[Cell] = gameState.upcoming
}

class GameState(val upcoming: List[Cell]= List(Empty(), Empty(), Empty(), Empty(), Empty())) {}

object PipeLogic {
  val FramesPerSecond: Int = 5 // change this to speed up or slow down the game

  def apply() = new PipeLogic()
}