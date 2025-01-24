package minesweeper

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

    for(i in 1..9){
        for(j in 1..9){
            print(board[i][j])
        }
        println()
    }
}
