package pipes.game

import java.awt.event
import java.awt.event.KeyEvent._
import engine.GameBase
import processing.core.{PApplet, PImage}
import processing.event.KeyEvent
import pipes.logic._
import engine.graphics.{Button, ImageManager, Menu, TextBox}

class PipeGame extends GameBase {

  var gameLogic: PipeLogic = PipeLogic()
  val updateTimer = new UpdateTimer(PipeLogic.FramesPerSecond.toFloat)

  val menuList: List[Menu] = Menu()

  var loadedImageList: Map[String, PImage] = Map[String, PImage]()
  //val gridDims : Dimensions = gameLogic.gridDims
  //val widthInPixels: Int = (WidthCellInPixels * gridDims.width).ceil.toInt
  //val heightInPixels: Int = (HeightCellInPixels * gridDims.height).ceil.toInt

  override def draw(): Unit = {
    updateState()
    drawMenu()
  }

  def drawMenu(): Unit = {
    loadedImageList("test").resize(gameWidth, gameHeight)
    background(loadedImageList("test"))
    textSize(55);
    menuList.foreach(findMenu)
  }

  def findMenu(item: Menu): Unit = item match {
    case item: TextBox => text(item.text, item.position.x.toFloat, item.position.y.toFloat)
    case item: Button => image(loadedImageList(item.image), item.position.x.toFloat, item.position.y.toFloat)
  }

  /*def drawGameOverScreen(): Unit = {
    setFillColor(Color.Red)
    drawTextCentered("GAME OVER!", 20, screenArea.center)
  }

  def drawGrid(): Unit = {

    val widthPerCell = screenArea.width / gridDims.width
    val heightPerCell = screenArea.height / gridDims.height

    for (p <- gridDims.allPointsInside) {
      drawCell(getCell(p), gameLogic.getCellType(p))
    }

    def getCell(p : GridPoint): Rectangle = {
      val leftUp = Point(screenArea.left + p.x * widthPerCell,
        screenArea.top + p.y * heightPerCell)
      Rectangle(leftUp, widthPerCell, heightPerCell)
    }

    def drawCell(area: Rectangle, tetrisColor: CellType): Unit = {
      val color = tetrisBlockToColor(tetrisColor)
      setFillColor(color)
      drawRectangle(area)
    }

  }*/

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