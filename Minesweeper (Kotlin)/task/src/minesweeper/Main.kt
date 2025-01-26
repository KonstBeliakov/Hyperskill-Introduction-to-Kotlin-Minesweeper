package minesweeper

fun displayBoard(board: MutableList<MutableList<Char>>) {
    println(" │123456789│")
    println("—│—————————│")
    for (i in 1..9) {
        print("$i│")
        for (j in 1..9) {
            print(board[i][j])
        }
        println("│")
    }
    println("—│—————————│")
}

fun main() {
    println("How many mines do you want on the field?")
    val mines = readln().toInt()

    val coordinates = List(9 * 9){i  ->
        listOf(i % 9 + 1, i / 9 + 1)
    }

    val board = MutableList(9 + 2){
        MutableList(9 + 2){'.'}
    }

    val mineCoordinates = coordinates.shuffled().take(mines)
    for((x, y) in mineCoordinates)
        board[x][y] = 'X'

    for(x in 1..9){
        for(y in 1..9){
            if(board[x][y] != 'X'){
                var minesAround = 0
                for(i in -1..1){
                    for(j in -1..1){
                        if (board[x + i][y + j] == 'X') minesAround ++
                    }
                }
                if(minesAround != 0) board[x][y] = minesAround.toString().first()
            }
        }
    }

    val shownBoard = MutableList(9 + 2) { i ->
        MutableList(9 + 2) { j ->
            if (board[i][j] == 'X') '.' else board[i][j]
        }
    }

    var flags = 0
    var gameOver = false

    while(!gameOver) {
        displayBoard(shownBoard)
        println("Set/delete mines marks (x and y coordinates):")
        val line = readln().split(" ")
        val x = line[0].toInt()
        val y = line[1].toInt()
        if (board[x][y].isDigit()) {
            println("There is a number here!")
        } else if (shownBoard[x][y] == '*') {
            shownBoard[x][y] = '.'
            flags--
        } else {
            shownBoard[x][y] = '*'
            flags++
        }

        if (flags == mines) {
            if (List(mines) { i -> shownBoard[mineCoordinates[i][0]][mineCoordinates[i][1]] == '*' }.all { it }) {
                displayBoard(shownBoard)
                println("Congratulations! You found all the mines!")
                gameOver = true
            }

        }
    }
}
