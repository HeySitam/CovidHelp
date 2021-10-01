
fun main(args: Array<String>) {
    val num = 6
    val factorial = multiplyNumbers(num)
    println("Factorial of $num = $factorial")
}

fun multiplyNumbers(num: Int): Long {
    if (num >= 1)
        return num * multiplyNumbers(num - 1)
    else
        return 1
}
