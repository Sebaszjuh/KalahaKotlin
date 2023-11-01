package pit

import pit.state.BigPitState
import pit.state.PitState
import player.Player


class BigPit(stones: Int, player: Player, pitState: PitState) : Pit(stones, player, pitState) {
    constructor(player: Player) : this(DEFAULT_NUMBER_BIG_PIT_STONES, player, BigPitState())

    override fun canSow(player: Player): Boolean {
        return player == super.ownerOfPit
    }

    override fun handleLastSow(currentPlayer: Player) {
        handleSimpleSow(currentPlayer)
        currentPlayer.extraTurn = true
    }

    override fun takeStones(): Int {
        return 0
    }

    companion object {
        private const val DEFAULT_NUMBER_BIG_PIT_STONES = 0
    }
}

