@file:JvmName("Util")
package base.function


fun main() {
    val a = 11
    val b = 21

    println("Max: ${max(a, b)}")

    println("Min: ${min(a, b)}")
}

/**
 * top level functions
 */

/**
 * Эл-ое определение функции.
 */
fun max(a: Int, b: Int): Int {
    return if (a > b) a else b
}

/**
 * Если функция возвращает только одно выражение.
 */
fun min(a: Int, b: Int) = if (a < b) a else b