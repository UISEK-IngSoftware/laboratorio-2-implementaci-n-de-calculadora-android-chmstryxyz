package ec.edu.uisek.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class CalculatorState(
    val currentInput: String = "0",
    val fullExpression: String = ""
)

sealed class CalculatorEvent {
    data class Number(val number: String) : CalculatorEvent()
    data class Operator(val operator: String) : CalculatorEvent()
    object Clear : CalculatorEvent()
    object AllClear : CalculatorEvent()
    object Calculate : CalculatorEvent()
    object Decimal : CalculatorEvent()
}

class CalculatorViewModel : ViewModel() {

    var state by mutableStateOf(CalculatorState())
        private set

    private var number1: String = ""
    private var number2: String = ""
    private var operator: String? = null

    fun onEvent(event: CalculatorEvent) {
        when (event) {
            is CalculatorEvent.Number -> enterNumber(event.number)
            is CalculatorEvent.Operator -> enterOperator(event.operator)
            is CalculatorEvent.Decimal -> enterDecimal()
            is CalculatorEvent.AllClear -> clearAll()
            is CalculatorEvent.Clear -> clearLast()
            is CalculatorEvent.Calculate -> performCalculation()
        }
    }

    private fun enterNumber(number: String) {
        if (operator == null) {
            if (number1.length >= 12) return
            if (number1 == "0") number1 = ""
            number1 += number
            state = state.copy(currentInput = number1)
        } else {
            if (number2.length >= 12) return
            if (number2 == "0") number2 = ""
            number2 += number
            state = state.copy(currentInput = number2)
        }
    }

    private fun enterOperator(op: String) {
        if (number1.isNotBlank()) {
            operator = op
            state = state.copy(
                fullExpression = "$number1 $operator",
                currentInput = if (number2.isNotBlank()) number2 else "0"
            )
        }
    }

    private fun enterDecimal() {
        if (operator == null) {
            if (!number1.contains(".")) {
                if (number1.isBlank()) number1 = "0"
                number1 += "."
                state = state.copy(currentInput = number1)
            }
        } else {
            if (!number2.contains(".")) {
                if (number2.isBlank()) number2 = "0"
                number2 += "."
                state = state.copy(currentInput = number2)
            }
        }
    }

    private fun performCalculation() {
        val num1 = number1.toDoubleOrNull()
        val num2 = number2.toDoubleOrNull()

        if (num1 != null && num2 != null && operator != null) {
            val result = when (operator) {
                "+" -> num1 + num2
                "−" -> num1 - num2
                "×" -> num1 * num2
                "÷" -> if (num2 != 0.0) num1 / num2 else Double.NaN
                else -> 0.0
            }
            val resultString = if (result.isNaN()) "Error" else result.toString().removeSuffix(".0")
            clearAll()
            number1 = if (result.isNaN()) "" else resultString
            state = state.copy(currentInput = number1, fullExpression = "")
        }
    }

    private fun clearLast() {
        if (number2.isNotBlank()) {
            number2 = number2.dropLast(1)
            state = state.copy(currentInput = if (number2.isBlank()) "0" else number2)
        } else if (operator != null) {
            operator = null
            state = state.copy(fullExpression = "", currentInput = number1)
        } else if (number1.isNotBlank()) {
            number1 = number1.dropLast(1)
            state = state.copy(currentInput = if (number1.isBlank()) "0" else number1)
        }
    }

    private fun clearAll() {
        number1 = ""
        number2 = ""
        operator = null
        state = state.copy(currentInput = "0", fullExpression = "")
    }
}