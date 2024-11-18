package base.function.loops

fun main() {
    val strings = listOf("first", "second", "third")
    for (string in strings) {
        println(string)
    }
    for ((index, element) in strings.withIndex()) {
        println("index = $index, element = $element")
    }

    val map = mapOf(
        1 to "first",
        2 to "second",
        3 to "three"
    )
    for ((key, value) in map) {
        println("$key - $value")
    }

    println(isLetter('b'))
    println(isLetter('1'))
}

fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'
