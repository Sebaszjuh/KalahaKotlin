package pit.state

import pit.Pit
import player.Player


interface PitState {
    fun handleProcess(pit: Pit, player: Player)
    fun nextState(pit: Pit)
}
