package pipes.logic

abstract class Cell(var sourcePipe: Option[Direction], val connections: List[Direction], var filled: Float = 0) {
  def image: String

  def getConnection: Direction = connections.filterNot(_ == sourcePipe.get).head
}

case class Empty() extends Cell(None, List()) {
  override def image: String = "emptyCell"
}

case class PlusCell() extends Cell(None, List[Direction](North(), West(), South(), East())) {
  override def image: String = "+Cell"

  override def getConnection: Direction = sourcePipe.get.opposite()
}

case class ICell() extends Cell(None, List[Direction](North(), South())) {
  override def image: String = "ICell"
}

case class DashCell() extends Cell(None, List[Direction](West(), East())) {
  override def image: String = "-Cell"
}

case class NWCell() extends Cell(None, List[Direction](North(), West())) {
  override def image: String = "NWCell"
}

case class SWCell() extends Cell(None, List[Direction](South(), West())) {
  override def image: String = "SWCell"
}

case class SECell() extends Cell(None, List[Direction](South(), East())) {
  override def image: String = "SECell"
}

case class NECell() extends Cell(None, List[Direction](North(), East())) {
  override def image: String = "NECell"
}

abstract class Source(direction: Direction) extends Cell(None, List(direction))

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
