package board

import pit.Pit
import player.Player
import java.util.*


class BoardController(var board: Board) {
    fun playMove(pit: Pit) {
        processPlayerMove(pit)
        updateCurrentPlayer()
    }

    fun processPlayerMove(pit: Pit) {
        val player: Player = board.currentPlayer
        player.stonesInHand = pit.takeStones()
        var temp: Pit = pit
        while (player.stonesInHand > 0) {
            temp = temp.nextPit!!
            temp.updatePitProcess(player)
        }
        printBoard()
        updateBoardStatusIfFinished()
    }

    fun printWinner() {
        println("-----------------------")
        System.out.printf("The winner is.... %s\n", board.gameStatus)
        println("-----------------------")
    }

    fun printBoard() {
        val TEMPLATE = """
        ------------------------------------------------
                       Player Two
             | %02d | %02d | %02d | %02d | %02d | %02d |
        (%02d)                                 (%02d)
             | %02d | %02d | %02d | %02d | %02d | %02d |
                       Player One
        
        """.trimIndent()
        val pits: MutableList<Pit> = ArrayList<Pit>()
        val listP2: LinkedList<Pit> = LinkedList<Pit>(board.player2Board)
        val listP1: LinkedList<Pit> = LinkedList<Pit>(board.player1Board)
        val bigPit: Pit = listP2.pollLast()
        for (i in 0..<board.player2Board.size - 1) {
            pits.add(listP2.pollLast())
        }
        pits.add(bigPit)
        pits.add(listP1.pollLast())
        for (i in 0..<board.player1Board.size - 1) {
            pits.add(listP1.pollFirst())
        }
        System.out.printf(TEMPLATE, *pits.stream().map<Any>(Pit::stonesInPit).toArray())
    }

    fun cleanupBoard() {
        val p1Sum: Int = board.player1Board.stream().filter { pit ->
            pit.ownerOfPit == board.player1
        }.mapToInt(Pit::takeStones).sum()
        val p2Sum: Int = board.player2Board.stream().filter { pit ->
            pit.ownerOfPit == board.player2
        }.mapToInt(Pit::takeStones).sum()
        board.player1.bigPit!!.addStonesToPit(p1Sum)
        board.player2.bigPit!!.addStonesToPit(p2Sum)
    }

    fun updateBoardStatusWithWinner() {
        val p1Stones: Int = board.player1.bigPit!!.stonesInPit
        val p2Stones: Int = board.player2.bigPit!!.stonesInPit
        if (p1Stones == p2Stones) {
            board.gameStatus = BoardStatus.DRAW
            return
        }
        val boardStatus: BoardStatus =
            if (p1Stones > p2Stones) BoardStatus.WIN_PLAYER_ONE else BoardStatus.WIN_PLAYER_TWO
        board.gameStatus = boardStatus
    }

    fun updateBoardStatusIfFinished() {
        val p1Finished = isPlayerFinished(board.player1Board)
        val p2Finished = isPlayerFinished(board.player2Board)
        if (p1Finished || p2Finished) {
            board.gameStatus = BoardStatus.FINISHED
        }
    }

    private fun isPlayerFinished(list: List<Pit>): Boolean {
        return list.stream().limit((list.size - 1).toLong()).mapToInt(Pit::stonesInPit).allMatch { x: Int -> x == 0 }
    }

    fun updateCurrentPlayer() {
        val currentPlayer: Player = board.currentPlayer
        if (currentPlayer.isExtraTurn()) {
            return
        }
        board.currentPlayer = if (currentPlayer == board.player1) board.player2 else board.player1
    }

}
