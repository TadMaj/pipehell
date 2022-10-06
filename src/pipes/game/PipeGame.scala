package pipes.game

import java.awt.event
import java.awt.event.KeyEvent._
import engine.GameBase
import processing.core.{PApplet, PImage}
import processing.event.{KeyEvent, MouseEvent}
import pipes.logic._
import engine.graphics._
import engine.helpers._

class PipeGame extends GameBase {
  var gameLogic: PipeLogic = PipeLogic()

  val updateTimer = new UpdateTimer(PipeLogic.FramesPerSecond.toFloat)

  val menuList: List[Menu] = Menu()

  var loadedImageList: Map[String, PImage] = Map[String, PImage]()

  var appState: AppState = StateMenu

  def setupStartGame(): Unit = {
    gameLogic = new PipeLogic()
  }

  //val gridDims : Dimensions = gameLogic.gridDims
  //val widthInPixels: Int = (WidthCellInPixels * gridDims.width).ceil.toInt
  //val heightInPixels: Int = (HeightCellInPixels * gridDims.height).ceil.toInt

  override def draw(): Unit = {
    updateState()
    if (appState == StateMenu) drawMenu()
    if (appState == StateGame) drawGame()
  }

  def drawMenu(): Unit = {
    background(loadedImageList("background"))
    textSize(55);
    menuList.foreach(findMenu)
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
  }

  def drawScore(): Unit = {
    textSize(40);
    text("Score: " + gameLogic.score, 150, 40)
    text("Required chain: " + gameLogic.required, 550, 40)
  }

  def drawGrid(): Unit = {
    val pointOffset = Point(150, 57)
    val cellRightOffSet = Point(100, 0)
    val cellDownOffSet = Point(0, 100)

    for (i <- 0 until 7) {
      for (x <- 0 until 10) {
        val element = gameLogic.getCellType(Point(i, x))
        if (element == 0) {
          val newPoint = pointOffset + cellRightOffSet*x + cellDownOffSet*i
          image(loadedImageList("emptyCell"), newPoint.x.toFloat, newPoint.y.toFloat)
        }
      }
    }
  }
  /** Method that calls handlers for different key press events.
    * You may add extra functionality for other keys here.
    * See [[event.KeyEvent]] for all defined keycodes.
    *
    * @param event The key press event to handle
    */
  override def keyPressed(event: KeyEvent): Unit = {

    /*event.getKeyCode match {
      case VK_A     => gameLogic.rotateLeft()
      case VK_S     => gameLogic.rotateRight()
      case VK_UP    => gameLogic.rotateRight()
      case VK_DOWN  => gameLogic.moveDown()
      case VK_LEFT  => gameLogic.moveLeft()
      case VK_RIGHT => gameLogic.moveRight()
      case VK_SPACE => gameLogic.doHardDrop()
      case _        => ()
    }*/

  }

  override def mouseClicked(event: MouseEvent): Unit = appState match {
    case StateMenu => checkForMenuButtonClick()
  }

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
    // If line below gives errors try size(widthInPixels, heightInPixels, PConstants.P2D)
    size(1200, 800)
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
      //gameLogic.moveDown()
      updateTimer.advanceFrame()
    }
  }
}

object PipeGame {


  val WidthCellInPixels: Double = 15 * PipeLogic.DrawSizeFactor
  val HeightCellInPixels: Double = WidthCellInPixels

  def main(args:Array[String]): Unit = {
    PApplet.main("pipes.game.PipeGame")
  }

}