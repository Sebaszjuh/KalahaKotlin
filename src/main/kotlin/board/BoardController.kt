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
        val listP2: LinkedList<Pit> = LinkedList<Pit>(board.boardP2)
        val listP1: LinkedList<Pit> = LinkedList<Pit>(board.boardP1)
        val bigPit: Pit = listP2.pollLast()
        for (i in 0..<board.boardP2.size - 1) {
            pits.add(listP2.pollLast())
        }
        pits.add(bigPit)
        pits.add(listP1.pollLast())
        for (i in 0..<board.boardP1.size - 1) {
            pits.add(listP1.pollFirst())
        }
        System.out.printf(TEMPLATE, *pits.stream().map<Any>(Pit::stonesInPit).toArray())
    }

    fun cleanupBoard() {
        val p1Sum: Int = board.boardP1.stream().filter { pit ->
            pit.ownerOfPit == board.p1
        }.mapToInt(Pit::takeStones).sum()
        val p2Sum: Int = board.boardP2.stream().filter { pit ->
            pit.ownerOfPit == board.p2
        }.mapToInt(Pit::takeStones).sum()
        board.p1.bigPit!!.addStonesToPit(p1Sum)
        board.p2.bigPit!!.addStonesToPit(p2Sum)
    }

    fun updateBoardStatusWithWinner() {
        val p1Stones: Int = board.p1.bigPit!!.stonesInPit
        val p2Stones: Int = board.p2.bigPit!!.stonesInPit
        if (p1Stones == p2Stones) {
            board.gameStatus = BoardStatus.DRAW
            return
        }
        val boardStatus: BoardStatus =
            if (p1Stones > p2Stones) BoardStatus.WIN_PLAYER_ONE else BoardStatus.WIN_PLAYER_TWO
        board.gameStatus = boardStatus
    }

    fun updateBoardStatusIfFinished() {
        val p1Finished = isPlayerFinished(board.boardP1)
        val p2Finished = isPlayerFinished(board.boardP2)
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
        board.currentPlayer = if (currentPlayer == board.p1) board.p2 else board.p1
    }

}
