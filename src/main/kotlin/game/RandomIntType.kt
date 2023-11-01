package game

import board.BoardController
import pit.Pit

class RandomIntType : PlayType() {

    override fun playGame(boardController: BoardController) {
        val pit: Pit = getRandomGeneratedValidPit(boardController)
        boardController.playMove(pit)
    }
}