package board

import pit.Pit
import player.Player
import java.util.*

data class Board(
    var boardP1: LinkedList<Pit>,
    var boardP2: LinkedList<Pit>,
    var p1: Player,
    var p2: Player
) {

    var gameStatus: BoardStatus = BoardStatus.ACTIVE

    val activePlayerBoard: LinkedList<Pit>
        get() = if (currentPlayer == p1) boardP1 else boardP2

    var currentPlayer: Player = p1

}