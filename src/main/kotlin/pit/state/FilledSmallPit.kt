package pit.state

import exception.KalahaPitStateException
import pit.Pit
import player.Player


class FilledSmallPit : PitState {

    override fun handleProcess(pit: Pit, player: Player) {
        pit.handleSimpleSow(player)
    }

    override fun nextState(pit: Pit) {
        if (pit.stonesInPit > 0) {
            throw KalahaPitStateException("Can't update state to empty, Pit has stones remaining")
        }
        pit.pitState = EmptySmallPit()
    }
}
