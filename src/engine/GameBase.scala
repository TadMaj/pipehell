package engine

import processing.core.PApplet

class GameBase extends PApplet {
  val gameWidth = 1200
  val gameHeight = 800

  // inner class: can call current time of outer class
  class UpdateTimer(val framesPerSecond: Float) {

    val frameDuration: Float = 1000 / framesPerSecond // ms
    var nextFrame: Float = Float.MaxValue

    def init(): Unit = nextFrame = currentTime() + frameDuration
    def timeForNextFrame(): Boolean = currentTime() >= nextFrame
    def advanceFrame(): Unit = nextFrame = nextFrame + frameDuration

  }

  // ===Processing Wrappers & Abstractions===

  /** An alias for the obscurely named function millis()
    *
    * @return Current time in milliseconds since stating the program.
    */
  def currentTime(): Int = millis()

  /*def drawTextShadow(string: String, pos: Point, color: Color = Black, thickness: Float = 1): Unit = {
    pushStyle()
    setFillColor(color)
    List((1,0),(-1,0),(0,1),(0,-1)).foreach(t => {
      text(string, pos.x+(t._1*thickness), pos.y+t._2*thickness)
    })
    popStyle()
  }*/

}
