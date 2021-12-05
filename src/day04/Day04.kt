package day04

import readInput

fun main() {
    val exampleInput = readInput("Day04_example", "day04")
    var (numbers, boards) = parseInput(exampleInput)
    println("Result for example values: ${part1(numbers, boards)}")

    val input = readInput("Day04_test", "day04")
    val (numbersData, boardsData) = parseInput(input)
    println("Result for real data values: ${part1(numbersData, boardsData)}")

    println("----------")
    println("Result for example values: ${part2(numbers, boards)}")
    println("Result for real data values: ${part2(numbersData, boardsData)}")
}

fun parseInput(input: List<String>): Pair<List<Int>, List<Board>> {
    val numbers = input[0].split(",").map {
        it.toInt()
    }

    val boards = mutableListOf<Board>()
    var currentBoardValues = mutableListOf<MutableList<Int>>()
    for (index in 2 until input.size) {
        val currentRow = input[index].split(" ").filter { it != "" }.map { it.toInt() }.toMutableList()
        if (!currentRow.isEmpty()) {
            currentBoardValues.add(currentRow)
        }

        if (input[index].isEmpty()) {
            boards.add(Board(currentBoardValues))
            currentBoardValues = mutableListOf<MutableList<Int>>()
        }
    }

    boards.add(Board(currentBoardValues))
    return Pair(numbers, boards)
}

data class Board(val values: MutableList<MutableList<Int>>)

fun part1(numbers: List<Int>, boards: List<Board>): Int {
    numbers.forEach { number ->
        number
        boards.forEach {
           board ->
            board.values.forEachIndexed() {
                rowIndex, row -> row.forEachIndexed {
                colIndex, value ->
                    if (value == number) {
                        board.values[rowIndex][colIndex] = 0
                        if(isBingo(board)) {
                            return sumAll(board) * number
                        }
                    }
            }
            }

        }
    }
    return -1
}

fun sumAll(board: Board): Int {
    var total = 0
    board.values.forEach {
        total += it.sum()
    }

    return total
}

fun isBingo(board: Board): Boolean {
    board.values.forEach {
        if (setIsBingo(it)) {
            return true
        }
    }

    for (index in board.values.indices) {
        val set = mutableListOf(board.values[0][index], board.values[1][index], board.values[2][index], board.values[3][index], board.values[4][index])
        if (setIsBingo(set)) {
            return true
        }
    }

    return false
}

fun setIsBingo(it: MutableList<Int>): Boolean {
   return it.all { it == 0 }
}

fun part2(numbers: List<Int>, boards: List<Board>): Int  {
    val indexesToSkip = mutableSetOf<Int>()
    var lastWinningBoard = Board(mutableListOf())
    var lastNumber = -1

    numbers.forEach { number ->
        boards.forEachIndexed {
                boardIndex, board ->
            run forBoard@ {
                if (boardIndex in indexesToSkip) return@forBoard
                board.values.forEachIndexed() { rowIndex, row ->
                    row.forEachIndexed { colIndex, value ->
                        if (value == number) {
                            board.values[rowIndex][colIndex] = 0
                            if (isBingo(board)) {
                                indexesToSkip.add(boardIndex)
                                lastWinningBoard = board
                                lastNumber = number
                            }
                        }
                    }
                }
            }


        }
    }
    return sumAll(lastWinningBoard) * lastNumber
}

