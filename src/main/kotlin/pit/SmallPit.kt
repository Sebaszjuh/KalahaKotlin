package pit

import exception.KalahaIllegalMoveException
import pit.state.FilledSmallPit
import pit.state.PitState
import player.Player


class SmallPit internal constructor(stones: Int, player: Player, state: PitState) : Pit(stones, player, state) {
    constructor(player: Player) : this(DEFAULT_NUMBER_OF_PITS, player, FilledSmallPit())

    override fun handleLastSow(currentPlayer: Player) {
        if (stonesInPit > 0) {
            throw KalahaIllegalMoveException("Invalid move, pit must have 0 stones to make this move")
        }
        val opponentPit: Pit? = oppositePit
        val opponentStones = opponentPit?.takeStones()
        currentPlayer.decrementStonesInHand()
        if (opponentStones != null) {
            currentPlayer.bigPit!!.addStonesToPit(opponentStones + 1)
        }
    }

    override fun takeStones(): Int {
        val nrOfStones = stonesInPit
        if (nrOfStones == 0) {
            return 0
        }
        stonesInPit = 0
        pitState.nextState(this)
        return nrOfStones
    }

    override fun canSow(player: Player): Boolean {
        return true
    }

    companion object {
        private const val DEFAULT_NUMBER_OF_PITS = 6
    }
}
