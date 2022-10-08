package pipes.logic

import engine.helpers.Point

abstract class Direction {
    def relativeToPoint(): Point = this match {
      case North() => Point(0, -1)
      case South() => Point(0, 1)
      case West() => Point(-1, 0)
      case East() => Point(1, 0)
    }

    def opposite(): Direction = this match {
      case North() => South()
      case South() => North()
      case West() => East()
      case East() => West()
    }
}

case class North() extends Direction
case class South() extends Direction
case class West() extends Direction
case class East() extends Direction