package day21

import java.io.File

internal data class Player(val name: String, var pos: Int, var score: Int = 0) {
    fun move(amount: Int) {
        pos += amount % 10
        if (pos % 10 != 0)
            pos %= 10
    }

    fun addScore() {
        score += pos
    }
}

internal data class DeterministicGame(
    val player1: Player,
    val player2: Player,
    var nbrOfDiceRolls: Int,
    var diceSide: Int
) {

    fun movePlayer1(): Boolean = movePlayer(player1)
    fun movePlayer2(): Boolean = movePlayer(player2)

    private fun movePlayer(player: Player): Boolean {
        var move = 0
        for (i in 0..<3) {
            move += diceSide
            diceSide++
            if (diceSide == 101)
                diceSide = 1
        }

        nbrOfDiceRolls += 3
        player.move(move)
        player.addScore()

        return player.score >= 1000
    }
}

internal data class QuantumGame(val player1: Player, val player2: Player, var nbrOfDiceRolls: Int) : Cloneable {

    companion object {
        var p1Wins = 0
        var p2Wins = 0
    }

    fun rollDice() {
        val newGamesP1 = rollDicePlayer1()

        val newGamesP2 = ArrayList<QuantumGame>()

        newGamesP1.forEach { game ->
            newGamesP2.addAll(game.rollDicePlayer2())
        }

//        println("checking ${newGamesP2.size} from player2")

        newGamesP2.forEach { game ->
            game.rollDice()
        }
    }

    fun rollDicePlayer1(nbrOfRolls: Int = 1): ArrayList<QuantumGame> {
        val allGames = ArrayList<QuantumGame>()

        val c1 = this.clone() as QuantumGame
        c1.movePlayer1(1)
        if (nbrOfRolls < 3)
            allGames.addAll(c1.rollDicePlayer1(nbrOfRolls + 1))

        val c2 = this.clone() as QuantumGame
        c2.movePlayer1(2)
        if (nbrOfRolls < 3)
            allGames.addAll(c2.rollDicePlayer1(nbrOfRolls + 1))

        val c3 = this.clone() as QuantumGame
        c3.movePlayer1(3)
        if (nbrOfRolls < 3)
            allGames.addAll(c3.rollDicePlayer1(nbrOfRolls + 1))

        if (nbrOfRolls == 3) {
            val unfinishedGames = arrayListOf(c1, c2, c3)

            unfinishedGames.removeIf { game ->
                game.player1.addScore()
                if (game.player1HasWon()) {
                    p1Wins++
                    true
                } else if (game.player2HasWon()) {
                    p2Wins++
                    true
                } else
                    false
            }

            allGames.addAll(unfinishedGames)
        }

        return allGames
    }

    fun rollDicePlayer2(nbrOfRolls: Int = 1): ArrayList<QuantumGame> {
        val allGames = ArrayList<QuantumGame>()

        val c1 = this.clone() as QuantumGame
        c1.movePlayer2(1)
        if (nbrOfRolls < 3)
            allGames.addAll(c1.rollDicePlayer2(nbrOfRolls + 1))

        val c2 = this.clone() as QuantumGame
        c2.movePlayer2(2)
        if (nbrOfRolls < 3)
            allGames.addAll(c2.rollDicePlayer2(nbrOfRolls + 1))

        val c3 = this.clone() as QuantumGame
        c3.movePlayer2(3)
        if (nbrOfRolls < 3)
            allGames.addAll(c3.rollDicePlayer2(nbrOfRolls + 1))

        if (nbrOfRolls == 3) {
            val unfinishedGames = arrayListOf(c1, c2, c3)

            unfinishedGames.removeIf { game ->
                game.player2.addScore()
                if (game.player1HasWon()) {
                    p1Wins++
                    true
                } else if (game.player2HasWon()) {
                    p2Wins++
                    true
                } else
                    false
            }

            allGames.addAll(unfinishedGames)
        }

        return allGames
    }

    private fun player1HasWon(): Boolean = player1.score >= 21
    private fun player2HasWon(): Boolean = player2.score >= 21
    private fun movePlayer1(amount: Int) = movePlayer(player1, amount)
    private fun movePlayer2(amount: Int) = movePlayer(player2, amount)

    private fun movePlayer(player: Player, amount: Int) {
        nbrOfDiceRolls++
        player.move(amount)
    }

    override fun clone(): Any {
        return QuantumGame(player1.copy(), player2.copy(), nbrOfDiceRolls)
    }
}

fun main() {
    playPart1()
    playPart2()
}

private fun playPart2() {
    val players = getPlayers()
    val game = QuantumGame(players.first, players.second, 0)

    game.rollDice()
    println("${QuantumGame.p1Wins}, ${QuantumGame.p2Wins}")


}

private fun playPart1() {
    val players = getPlayers()
    val game = DeterministicGame(players.first, players.second, 0, 1)

    while (true) {
        if (game.movePlayer1()) {
            println("${game.player2.name} lost with a score of ${game.player2.score}. ${game.player2.score}*${game.nbrOfDiceRolls}=${game.player2.score * game.nbrOfDiceRolls}")
            break
        }

        if (game.movePlayer2()) {
            println("${game.player1.name} lost with a score of ${game.player1.score}. ${game.player1.score}*${game.nbrOfDiceRolls}=${game.player1.score * game.nbrOfDiceRolls}")
            break
        }
    }
}

private fun getPlayers(): Pair<Player, Player> {
    val lines = File("src/main/kotlin/day21/input.txt").bufferedReader().readLines()
    val startingPos1 = lines[0].split(":").drop(1).first().trim().toInt()
    val player1 = Player("Player 1", startingPos1)

    val startingPos2 = lines[1].split(":").drop(1).first().trim().toInt()
    val player2 = Player("Player 2", startingPos2)

    return Pair(player1, player2)
}