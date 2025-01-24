package minesweeper

fun main() {
    println("How many mines do you want on the field?")
    val mines = readln().toInt()

    val coordinates = List(9 * 9){i  ->
        listOf(i % 9, i / 9)
    }

    val board = MutableList(9){
        MutableList(9){'.'}
    }

    val mineCoordinates = coordinates.shuffled().take(mines)
    for((x, y) in mineCoordinates)
        board[x][y] = 'X'

    for(line in board)
        println(line.joinToString(""))
}
