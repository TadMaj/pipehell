package engine.graphics

import engine.helpers.Point

abstract class Menu(val position: Point)

case class Button(point: Point, image: String = "button") extends Menu(position = point)

case class TextBox(point: Point, text: String) extends Menu(position = point)

case class Image(point: Point, image: String) extends Menu(position = point)

object Menu {
  def apply(): List[Menu] = List[Menu](
    Button(Point(400, 450)),
    TextBox(Point(450, 500), "Start Game"),
    Button(Point(400, 650)),
    TextBox(Point(540, 700), "Quit"),
    Image(Point(250, 100), "title")
  )
}

object GameOverMenu {
  def apply(): List[Menu] = List[Menu](
    Button(Point(400, 450)),
    TextBox(Point(530, 500), "Retry"),
    Button(Point(400, 650)),
    TextBox(Point(540, 700), "Quit"),
    //Image(Point(250, 100), "")
  )
}

