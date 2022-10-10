package engine.helpers

case class Point (x: Int, y: Int){
  def +(that: Point): Point = {
    Point(this.x + that.x, this.y + that.y)
  }

  def -(that: Point): Point = {
    Point(this.x - that.x, this.y - that.y)
  }

  def *(that: Int): Point = {
    Point(this.x * that, this.y * that)
  }
}
