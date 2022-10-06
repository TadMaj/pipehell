package pipes.logic

abstract class Cell(var filled: Float) {
  def image: String

  def fill(n: Int): Unit = if (filled + n < 1) filled += n else filled = 1
}

case class Empty() extends Cell(0) {
  override def image: String = "emptyCell"
}
