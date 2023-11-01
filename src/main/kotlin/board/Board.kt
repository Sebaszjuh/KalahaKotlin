package board

import pit.Pit
import player.Player
import java.util.*

data class Board(
    private var boardP1: LinkedList<Pit>,
    private var boardP2: LinkedList<Pit>,
    private var p1: Player,
    private var p2: Player
) {

    var gameStatus: BoardStatus = BoardStatus.ACTIVE

    val activePlayerBoard: LinkedList<Pit>
        get() = if (currentPlayer == p1) boardP1 else boardP2

    val player2Board: LinkedList<Pit>
        get() = boardP2

    val player1Board: LinkedList<Pit>
        get() = boardP1

    val player1: Player
        get() = p1

    val player2: Player
        get() = p2

    var currentPlayer: Player = p1

}