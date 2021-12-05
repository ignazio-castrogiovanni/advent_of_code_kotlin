package day02

import readInput

var x = 0
var y = 0
var aim = 0

fun main() {
    fun runCommand(command: String) {
        val commandParams = command.split(" ")
        val commandFunction = commandParams[0]
        val amount = commandParams[1].toInt()

        when (commandFunction) {
            "down" -> y += amount
            "up" -> y -= amount
            "forward" -> x += amount
        }
    }

    fun runCommandWithAim(command: String) {
        val commandParams = command.split(" ")
        val commandFunction = commandParams[0]
        val amount = commandParams[1].toInt()

        when (commandFunction) {
            "down" -> aim += amount
            "up" -> aim -= amount
            "forward" -> {
                x += amount
                y += amount * aim
            }
        }
    }

    fun part1(input: List<String>): Int {
        input.forEach {
            runCommand(it)
        }

        return x * y
    }

    fun part2(input: List<String>): Int {
        input.forEach {
            runCommandWithAim(it)
        }

        return x * y
    }


    val input = readInput("Day02_test", "day02")
    println(part1(input))

    x = 0
    y = 0
    println(part2(input))
}
