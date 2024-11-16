package base.variables

/**
 * val = value - immutable (read-only)
 * var = variable - mutable
 */
fun main() {
    val question = "life, the universe, and everything"

    println("$question ?")

//    question = "new question" compilation error

    var answer = 0
    answer = 42
    println("answer = $answer")

    /**
     * languages object can be changed
     */
    val languages = mutableListOf("Java")
    languages.add("Kotlin")
    println(languages)
}