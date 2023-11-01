package pit.state

import pit.Pit
import player.Player

class BigPitState : PitState {

    override fun handleProcess(pit: Pit, player: Player) {
        val stones = player.stonesInHand
        if (stones == 1) {
            pit.handleLastSow(player)
            return
        }
        pit.handleSimpleSow(player)
    }

    override fun nextState(pit: Pit) {
        return
    }
}