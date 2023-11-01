package pit

import pit.state.PitState
import player.Player


abstract class Pit internal constructor(
    var stonesInPit: Int,
    var ownerOfPit: Player,
    var pitState: PitState
) {
    var nextPit: Pit? = null
    var oppositePit: Pit? = null


    abstract fun canSow(player: Player): Boolean
    abstract fun handleLastSow(currentPlayer: Player)

    fun updatePitProcess(currentPlayer: Player) {
        check(currentPlayer.stonesInHand > 0) { "Cant process move, when hand is empty" }
        if (!canSow(currentPlayer)) {
            return
        }
        pitState.handleProcess(this, currentPlayer)
    }

    fun handleSimpleSow(currentPlayer: Player) {
        sow()
        currentPlayer.decrementStonesInHand()
    }

    abstract fun takeStones(): Int

    private fun sow() {
        stonesInPit++
    }

    fun addStonesToPit(addedStones: Int) {
        stonesInPit += addedStones
    }
}
