package pipes.game

import engine.GameBase
import processing.core.{PApplet, PImage}
import processing.event.MouseEvent
import pipes.logic._
import engine.graphics._
import engine.helpers._

class PipeGame extends GameBase {
  var gameLogic: PipeLogic = PipeLogic(0, 0)

  val updateTimer = new UpdateTimer(PipeLogic.FramesPerSecond.toFloat)

  val menuList: List[Menu] = Menu()

  val gameOverMenuList: List[Menu] = GameOverMenu()

  var loadedImageList: Map[String, PImage] = Map[String, PImage]()

  var appState: AppState = StateMenu

  var level = 0

  def setupStartGame(): Unit = {
    gameLogic = new PipeLogic(level, 0)
  }

  override def draw(): Unit = {
    updateState()
    if (appState == StateMenu) drawMenu()
    if (appState == StateGame) drawGame()
    if (appState == StateGameOver) drawGameOver()
  }

  def drawMenu(): Unit = {
    background(loadedImageList("background"))
    textSize(55)
    menuList.foreach(findMenu)
  }

  def drawGameOver(): Unit = {
    background(loadedImageList("background"))
    textSize(55)
    gameOverMenuList.foreach(findMenu)
  }


  def findMenu(item: Menu): Unit = item match {
    case item: TextBox => text(item.text, item.position.x.toFloat, item.position.y.toFloat)
    case item: Button => image(loadedImageList(item.image), item.position.x.toFloat, item.position.y.toFloat)
    case item: Image => image(loadedImageList(item.image), item.position.x.toFloat, item.position.y.toFloat)
  }

  def drawGame(): Unit = {
    background(150)
    image(loadedImageList("border"), 0, 0)
    drawGrid()
    drawScore()
    drawUpcoming()
  }

  def drawScore(): Unit = {
    fill(255)
    textSize(40)
    text("Score: " + gameLogic.score, 150, 40)
    text("Required chain: " + gameLogic.required, 550, 40)
  }

  def drawUpcoming(): Unit = {
    val originPoint = Point(30, 80)
    val upcoming: List[Cell] = gameLogic.getUpcoming
    for (i <- 0 until 5) image(loadedImageList(upcoming(i).image), originPoint.x.toFloat, (originPoint.y+100*i).toFloat)
  }

  def drawGrid(): Unit = {
    val pointOffset = Point(150, 57)
    val cellRightOffSet = Point(100, 0)
    val cellDownOffSet = Point(0, 100)

    fill(0, 0, 0)
    rect(pointOffset.x.toFloat, pointOffset.y.toFloat, cellRightOffSet.x.toFloat*10, cellDownOffSet.y.toFloat*7)

    for (i <- 0 until 7) {
      for (x <- 0 until 10) {
        val element = gameLogic.getCellType(Point(x, i))
        val newPoint = pointOffset + cellRightOffSet*x + cellDownOffSet*i
        if (element.filled != 0) {
          fill(color(0, 0, 255, 255 * element.filled))
          rect(newPoint.x.toFloat, newPoint.y.toFloat, 101, 101)
        }
        image(loadedImageList(element.image), newPoint.x.toFloat, newPoint.y.toFloat)
      }
    }
  }

  override def mouseClicked(event: MouseEvent): Unit = appState match {
    case StateGame => checkForGameButtonClick()
    case _ => checkForMenuButtonClick()
  }

  def checkForGameButtonClick(): Unit = if (checkIfMouseInBounds(Point(150, 57), Point(1150, 757))) gameLogic.mouseClick(Point((mouseX-150)/100, (mouseY-57)/100))

  def checkForMenuButtonClick(): Unit = {
    if (checkIfMouseInBounds(Point(400, 450), Point(800, 510))) {
      appState = StateGame
      setupStartGame()
    }
    if (checkIfMouseInBounds(Point(400, 650), Point(800, 710))) exit()
  }

  def checkIfMouseInBounds(topLeft: Point, bottomRight: Point): Boolean = topLeft.x < mouseX && bottomRight.x > mouseX && topLeft.y < mouseY && bottomRight.y > mouseY

  override def settings(): Unit = {
    pixelDensity(displayDensity())
    size(gameWidth, gameHeight)
  }

  override def setup(): Unit = {
    text("", 0, 0)

    loadedImageList = ImageManager.ImageList.foldLeft(Map[String, PImage]()) {(accumulator, image) =>
      accumulator.updated(image._1, loadImage(image._2))
    }
    updateTimer.init()
  }

  def updateState(): Unit = {
    if (updateTimer.timeForNextFrame()) {
      if (appState == StateGame) {
        gameLogic.advanceTime()
        checkForGameOver()
      }
      updateTimer.advanceFrame()
    }
  }

  def checkForGameOver(): Unit = {
    if (!gameLogic.gameOver) return
    if (gameLogic.goalReached()) {
      level += 1
      gameLogic = new PipeLogic(level, gameLogic.score)
    } else appState = StateGameOver
  }
}

object PipeGame {
  def main(args:Array[String]): Unit = {
    PApplet.main("pipes.game.PipeGame")
  }

}