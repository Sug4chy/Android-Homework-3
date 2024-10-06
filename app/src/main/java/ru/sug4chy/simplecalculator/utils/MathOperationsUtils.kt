package ru.sug4chy.simplecalculator.utils

object MathOperationsUtils {

    fun performCalculation(expression: String): Double =
        with(expression.split(' ')) {
            check(this.size == 3) { "Пожалуйста, введите только 2 числа и знак операции между ними." }

            val firstOperand = prepareAndCastOperand(this.first())
            val secondOperand = prepareAndCastOperand(this.last())

            when (this[1]) {
                "+" -> firstOperand + secondOperand
                "-" -> firstOperand - secondOperand
                "*" -> firstOperand * secondOperand
                "/" -> if (secondOperand != 0.0)
                    firstOperand / secondOperand
                else
                    throw IllegalArgumentException("На 0 разделить нельзя.")
                else -> throw IllegalArgumentException("Некорректная операция над числами.")
            }
        }

    private fun prepareAndCastOperand(operandString: String): Double {
        if (operandString.endsWith('.')) {
            return (operandString + "0").toDouble()
        }

        if (!operandString.contains('.')) {
            return ("$operandString.0").toDouble()
        }

        return operandString.toDouble()
    }
}