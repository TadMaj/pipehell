package pipes.logic

import engine.helpers.Point

abstract class Cell(val connections: List[Direction], var filled: Float = 0) {
  def image: String

  def fill(n: Int): Unit = if (filled + n < 1) filled += n else filled = 1

  def checkConnection(direction: Direction): Boolean = connections.contains(direction)
}

case class Empty() extends Cell(List()) {
  override def image: String = "emptyCell"
}

case class PlusCell() extends Cell(List[Direction](North(), West(), South(), East())) {
  override def image: String = "+Cell"
}

case class ICell() extends Cell(List[Direction](North(), South())) {
  override def image: String = "ICell"
}

case class DashCell() extends Cell(List[Direction](West(), East())) {
  override def image: String = "-Cell"
}

case class NWCell() extends Cell(List[Direction](North(), West())) {
  override def image: String = "NWCell"
}

case class SWCell() extends Cell(List[Direction](South(), West())) {
  override def image: String = "SWCell"
}

case class SECell() extends Cell(List[Direction](South(), East())) {
  override def image: String = "SECell"
}

case class NECell() extends Cell(List[Direction](North(), East())) {
  override def image: String = "NECell"
}

abstract class Source(direction: Direction) extends Cell(List(direction))

case class SourceNCell() extends Source(North()) {
  override def image: String = "SourceNCell"
}

case class SourceWCell() extends Source(West()) {
  override def image: String = "SourceWCell"
}

case class SourceECell() extends Source(East()) {
  override def image: String = "SourceECell"
}

case class SourceSCell() extends Source(South()) {
  override def image: String = "SourceSCell"
}
