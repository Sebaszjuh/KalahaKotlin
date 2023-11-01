package board


enum class BoardStatus {
    ACTIVE,
    FINISHED,
    DRAW,
    WIN_PLAYER_ONE,
    WIN_PLAYER_TWO;

    companion object {
        private val END_STATUS: Collection<BoardStatus> = listOf(DRAW, FINISHED, WIN_PLAYER_ONE, WIN_PLAYER_TWO)
        fun isGameEnd(status: BoardStatus): Boolean {
            return END_STATUS.contains(status)
        }
    }
}
