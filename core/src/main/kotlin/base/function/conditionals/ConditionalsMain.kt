package base.function.conditionals

/**
 * if в котлин - это выражение.
 */
fun main() {
    println(getDescription(Color.ORANGE))

    println(respondToInput("y"))
    println(respondToInput("n"))
    println(respondToInput("wooo"))
}

enum class Color {
    BLUE, ORANGE, RED
}

fun getDescription(color: Color): String {
    return when (color) {
        Color.BLUE -> "cold"
        Color.ORANGE -> "mild"
        Color.RED -> "hot"
    }
}

fun respondToInput(input: String): String {
    return when (input) {
        "y", "yes" -> "I'm glad you agree"
        "n", "no" -> "Sorry to hear that"
        else -> "I don't understand uou"
    }
}