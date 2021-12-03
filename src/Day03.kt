import kotlin.math.roundToInt

fun main() {
    val exampleInput = listOf(
        "00100", "11110", "10110", "10111", "10101", "01111",
        "00111", "11100", "10000", "11001", "00010", "01010")

    val input = readInput("Day03_test")

    println(part1(exampleInput))
    println(part1(input))

    println(part2(exampleInput))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    // binary string length of 12
    val binaryOnes = mutableListOf<Int>()
    for (i in input[0].indices) {
        binaryOnes.add(0)
    }
    var numberOfLines = 0

    input.forEach {
        numberOfLines++
        for (i in it.indices) {
            if (it[i] == '1') {
                binaryOnes[i]++
            }
        }
    }

    var binaryGamma = ""
    var binaryEpsilon = ""
    for (i in binaryOnes.indices) {
        val currentGamma = if (binaryOnes[i] > numberOfLines / 2) { "1" } else { "0" }
        val currentEpsilon = if (binaryOnes[i] > numberOfLines / 2) { "0" } else { "1" }

        binaryGamma += currentGamma
        binaryEpsilon += currentEpsilon
    }

    val gamma = binaryGamma.toInt(2)
    val epsilon = binaryEpsilon.toInt(2)

    return gamma * epsilon
}

fun part2(input: List<String>): Int {
    val oxygen = createBinaryString(input, true)

    val co2 = createBinaryString(input, false)

    val co2Value = co2[0].toInt(2)
    val oxygenValue = oxygen[0].toInt(2)

    return co2Value * oxygenValue
}

fun onlyOnes(it: String, currentCharacter: Int) = it[currentCharacter] == '1'

fun onlyZeros(it: String, currentCharacter: Int) = it[currentCharacter] == '0'

fun createBinaryString(list: List<String>, isOxygen: Boolean): List<String> {
    var variableList = list.toList()
    for (currentCharacter in variableList.indices) {
        if (variableList.size == 1) {
            break
        }
        var onesCounter = 0
        variableList.forEach {
            if (it[currentCharacter] == '1') {
                onesCounter++
            }
        }

        variableList = filterList(isOxygen, onesCounter, variableList, currentCharacter)

    }
    return variableList
}

private fun filterList(
    isOxygen: Boolean,
    onesCounter: Int,
    variableList: List<String>,
    currentCharacter: Int
) = if (isOxygen) {
    if (onesCounter >= (variableList.size / 2.0).roundToInt()) {
        variableList.filter { onlyOnes(it, currentCharacter) }
    } else {
        variableList.filter { onlyZeros(it, currentCharacter) }
    }
} else {
    if (onesCounter >= (variableList.size / 2.0).roundToInt()) {
        variableList.filter { onlyZeros(it, currentCharacter) }
    } else {
        variableList.filter { onlyOnes(it, currentCharacter) }
    }
}