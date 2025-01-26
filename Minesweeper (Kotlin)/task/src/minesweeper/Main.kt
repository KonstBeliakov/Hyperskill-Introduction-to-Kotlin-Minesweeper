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

fun freeCell(board: MutableList<MutableList<Char>>, x: Int, y: Int, shownBoard: MutableList<MutableList<Char>>) {
    shownBoard[x][y] = if (board[x][y] == '.') '/' else board[x][y]
    if (board[x][y] == '.' && x > 0 && x < 10 && y > 0 && y < 10) {
        for (i in -1..1) {
            for (j in -1..1) {
                if (shownBoard[x + i][y + j] == '.')
                    freeCell(board, x + i, y + j, shownBoard)
            }
        }
    }
}

fun generateBoard(board: MutableList<MutableList<Char>>, mines: Int) {
    for (x in 1..9) {
        for (y in 1..9)
            board[x][y] = '.'
    }

    val coordinates = List(9 * 9) { i ->
        listOf(i % 9 + 1, i / 9 + 1)
    }
    val mineCoordinates = coordinates.shuffled().take(mines)

    for ((x, y) in mineCoordinates)
        board[x][y] = 'X'

    for (x in 1..9) {
        for (y in 1..9) {
            if (board[x][y] != 'X') {
                var minesAround = 0
                for (i in -1..1) {
                    for (j in -1..1) {
                        if (board[x + i][y + j] == 'X') minesAround++
                    }
                }
                if (minesAround != 0) board[x][y] = minesAround.toString().first()
            }
        }
    }
}

fun checkFinished(board: MutableList<MutableList<Char>>, shownBoard: MutableList<MutableList<Char>>): Boolean {
    for (x in 1..9) {
        for (y in 1..9) {
            if ((board[x][y] == 'X') xor (shownBoard[x][y] == '*'))
                return false
        }
    }
    return true
}

fun main() {
    println("How many mines do you want on the field?")
    val mines = readln().toInt()

    val board = MutableList(9 + 2) {
        MutableList(9 + 2) { '.' }
    }

    generateBoard(board, mines)

    val shownBoard = MutableList(9 + 2) { i ->
        MutableList(9 + 2) { j -> '.' }
    }

    var gameOver = false
    var firstMove = true

    while (!gameOver) {
        displayBoard(shownBoard)
        println("Set/unset mine marks or claim a cell as free:")
        val line = readln().split(" ")
        val x = line[0].toInt()
        val y = line[1].toInt()
        val command = line[2]

        when (command) {
            "free" -> {
                if (firstMove) {
                    while (board[x][y] != '.') {
                        generateBoard(board, mines)
                    }
                    firstMove = false
                }
                if (board[x][y] == 'X') {
                    println("You stepped on a mine and failed!")
                    gameOver = true
                } else
                    freeCell(board, x, y, shownBoard)
            }

            "mine" -> {
                when(board[x][y]){
                    '1', '2', '3', '4', '5', '6', '7', '8', '9' -> println("There is a number here!")
                    '*' -> shownBoard[x][y] = '.'
                    else -> shownBoard[x][y] = '*'
                }
            }

            else -> println("Wrong command")
        }

        if (checkFinished(board, shownBoard)) {
            displayBoard(shownBoard)
            println("Congratulations! You found all the mines!")
            gameOver = true
        }
    }
}
