package engine.helpers


abstract class AppState()

case object StateMenu extends AppState()
case object StateGame extends AppState()
case object StateGameOver extends AppState()


