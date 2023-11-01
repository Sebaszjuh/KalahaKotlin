package board

import pit.BigPit
import pit.Pit
import pit.SmallPit
import player.Player
import java.util.*


object BoardFactory {
    private const val DEFAULT_NUMBER_OF_PITS = 6

    /**
     * Initializes the whole board with players, pits and links it circular
     * @return initialized board ready to play
     */
    fun createBoard(): Board {
        return createBoard(DEFAULT_NUMBER_OF_PITS)
    }

    fun createBoard(smallPits: Int): Board {
        val p1 = Player("Player 1")
        val p2 = Player("Player 2")
        val pitP1Board: LinkedList<Pit> = LinkedList<Pit>()
        val pitP2Board: LinkedList<Pit> = LinkedList<Pit>()
        fillBoardWithPits(pitP1Board, p1, smallPits)
        fillBoardWithPits(pitP2Board, p2, smallPits)
        linkOppositePit(pitP1Board, pitP2Board)
        makeCircular(pitP1Board, pitP2Board)
        return Board(pitP1Board, pitP2Board, p1, p2)
    }

    /**
     * Links opposite pits of each player
     * @param pitP1Board player 1 pits in a linkedlist
     * @param pitP2Board player 2 pits in a linkedlist
     */
    private fun linkOppositePit(pitP1Board: LinkedList<Pit>, pitP2Board: LinkedList<Pit>) {
        for (i in 0..<DEFAULT_NUMBER_OF_PITS) {
            val pit: Pit = pitP1Board[i]
            val oppositePit: Pit = pitP2Board[pitP2Board.size - 2 - i]
            pit.oppositePit = oppositePit
            oppositePit.oppositePit = pit
        }
    }

    private fun makeCircular(pitP1Board: LinkedList<Pit>, pitP2Board: LinkedList<Pit>) {
        connectCircular(pitP1Board, pitP2Board)
        connectCircular(pitP2Board, pitP1Board)
    }

    private fun connectCircular(pitList1: LinkedList<Pit>, pitList2: LinkedList<Pit>) {
        pitList1.getLast().nextPit = pitList2.getFirst()
    }

    private fun fillBoardWithPits(list: LinkedList<Pit>, player: Player, numberOfPits: Int) {
        var currentPit: Pit = SmallPit(player)
        list.add(currentPit)
        for (i in 0..<numberOfPits - 1) {
            val newPit: Pit = SmallPit(player)
            currentPit.nextPit = newPit
            list.add(newPit)
            currentPit = newPit
        }
        val bigPit = BigPit(player)
        currentPit.nextPit = bigPit
        list.add(bigPit)
        player.bigPit = bigPit
    }
}
