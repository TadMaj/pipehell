package engine.graphics

import engine.helpers.Point

abstract class Menu(val position: Point)

case class Button(point: Point, image: String = "button") extends Menu(position = point)

case class TextBox(point: Point, text: String) extends Menu(position = point)

object Menu {
  def apply(): List[Menu] = List[Menu](
    Button(Point(400, 400)),
    TextBox(Point(450, 450), "Start Game"),
    Button(Point(400, 500)),
    TextBox(Point(540, 550), "Quit"),
  )
}
