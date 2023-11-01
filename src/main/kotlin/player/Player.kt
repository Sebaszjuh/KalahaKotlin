package player

import pit.Pit


data class Player(var name: String) {
    var stonesInHand = 0
    var bigPit: Pit? = null
    var extraTurn = false

    fun decrementStonesInHand() {
        check(stonesInHand > 0) { "Cant decrement stones negatively" }
        stonesInHand--
    }

    fun isExtraTurn(): Boolean {
        val temp = extraTurn
        extraTurn = false
        return temp
    }
}
