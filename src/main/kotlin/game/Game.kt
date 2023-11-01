package game

import board.BoardController
import board.BoardFactory
import board.BoardStatus
import exception.KalahaIllegalGameException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class Game {

    private lateinit var boardController: BoardController
    private lateinit var playType: PlayType

    fun start() {
        playType = gameTypeInput();
        boardController = BoardController(BoardFactory.createBoard())
        startGame()
    }

    private fun startGame() {
        while (!BoardStatus.isGameEnd(boardController.board.gameStatus)) {
            playType.playGame(boardController)
        }
        afterGameIsFinished()
        boardController.printBoard()
        boardController.printWinner()
    }

    fun afterGameIsFinished() {
        if (!BoardStatus.isGameEnd(boardController.board.gameStatus)) {
            throw KalahaIllegalGameException("The game is not finished, illegal to cleanup")
        }
        boardController.cleanupBoard()
        boardController.updateBoardStatusWithWinner()
    }

    fun gameTypeInput(): PlayType {
        val template: String = """
                Please select which gametype u want to start
                1 - Multiplayer (You play by yourself against an opponent)
                2 - Random int generator (For demo purposes)
                3 - Singleplayer (Because why not?)
                """.trimIndent();
        println(template)
        var input: String
        do {
            val reader = BufferedReader(
                InputStreamReader(System.`in`)
            )
            input = try {
                reader.readLine()
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        } while (!InputValidation.validNumberInput(input))
        return getPlayType(input.toInt())
    }

    private fun getPlayType(type: Int): PlayType {
        return when (type) {
            1 -> MultiplayerType()
            2 -> RandomIntType()
            3 -> SinglePlayer()
            else -> throw IllegalArgumentException("Unknown or not yet implemented")
        }
    }
}