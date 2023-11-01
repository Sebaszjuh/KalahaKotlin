package game

import board.Board
import board.BoardController
import pit.Pit

class SinglePlayer : PlayType() {

    override fun playGame(boardController: BoardController) {
        val pit: Pit
        val board: Board = boardController.board
        pit = if (board.currentPlayer == board.player2) {
            getRandomGeneratedValidPit(boardController)
        } else {
            getPlayerPit(boardController)
        }
        boardController.playMove(pit)
    }
}