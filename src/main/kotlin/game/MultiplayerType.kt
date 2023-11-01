package game

import board.BoardController
import pit.Pit

class MultiplayerType : PlayType() {

    override fun playGame(boardController: BoardController) {
        val pit: Pit = getPlayerPit(boardController)
        boardController.playMove(pit)
    }
}