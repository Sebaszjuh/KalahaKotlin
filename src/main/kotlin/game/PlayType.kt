package game

import board.BoardController
import exception.KalahaIllegalMoveException
import pit.Pit
import player.Player
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.ThreadLocalRandom

abstract class PlayType {

    abstract fun playGame(boardController: BoardController)

    private fun pickRandomPitNumber(): Int {
        return ThreadLocalRandom.current().nextInt(0, 6)
    }

    protected fun getPlayerPit(boardController: BoardController): Pit {
        val currentPlayer: Player = boardController.board.currentPlayer
        val pickedPitNumber = getInputFromPlayer(currentPlayer)
        var pit: Pit = boardController.board.activePlayerBoard[pickedPitNumber]
        while (pit.stonesInPit == 0) {
            System.out.printf("You picked %s this is an incorrect number", pickedPitNumber)
            pit = boardController.board.activePlayerBoard[getInputFromPlayer(currentPlayer)]
        }
        return pit
    }

    private fun getInputFromPlayer(player: Player): Int {
        try {
            val reader = BufferedReader(
                InputStreamReader(System.`in`)
            )
            System.out.printf("%s please insert pit number between 0 and 5 to play\n", player.name)
            val s = reader.readLine()
            if (InputValidation.validNumberInput(s) && InputValidation.isValidRange(s)) {
                return s.toInt()
            }
            println("Invalid input, try again....")
            getInputFromPlayer(player)
        } catch (e: IOException) {
            throw KalahaIllegalMoveException("Incorrect input must be number between 0 and 5")
        }
        return 0
    }

    protected fun getRandomGeneratedValidPit(boardController: BoardController): Pit {
        var randomNumber = pickRandomPitNumber()
        System.out.printf("%s rolls %s\n", boardController.board.currentPlayer.name, randomNumber)
        var pit: Pit = boardController.board.activePlayerBoard[randomNumber]
        while (pit.stonesInPit == 0) {
            randomNumber = pickRandomPitNumber()
            System.out.printf(
                "%s has to roll again he rolls rolls %s\n",
                boardController.board.currentPlayer.name, randomNumber
            )
            pit = boardController.board.activePlayerBoard[randomNumber]
        }
        return pit
    }
}