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
  def currentTime(): Int = millis()
}
