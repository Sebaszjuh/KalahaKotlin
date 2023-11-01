package pit.state

import exception.KalahaPitStateException
import pit.Pit
import player.Player

class EmptySmallPit : PitState {

    override fun handleProcess(pit: Pit, player: Player) {
        val stones = player.stonesInHand;
        if (isAbleToCaptureStones(stones, pit, player)) {
            pit.handleLastSow(player)
        } else {
            pit.handleSimpleSow(player)
            nextState(pit)
        }
    }

    private fun isAbleToCaptureStones(stones: Int, pit: Pit, player: Player): Boolean {
        return stones == 1 && pit.stonesInPit == 0 && pit.ownerOfPit == player
    }

    override fun nextState(pit: Pit) {
        if (pit.stonesInPit == 0) {
            throw KalahaPitStateException("Can't update state to filled, Pit has no stones");
        }
        pit.pitState = FilledSmallPit()
    }
}