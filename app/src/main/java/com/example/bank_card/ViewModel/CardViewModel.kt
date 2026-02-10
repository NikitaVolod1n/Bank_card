package com.example.bank_card.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.bank_card.R

class CardViewModel() : ViewModel() {

    private val _state = mutableStateOf(CardState())
    val state = _state

    fun onNumberChange(input: String) {
        _state.value = _state.value.copy(
            number = input,
            numberError = validateNumber(input)
        )
        validate()
    }

    fun onMonthChange(input: String) {
        _state.value = _state.value.copy(
            month = input,
            monthError = validateMonth(input)
        )
        validate()
    }

    fun onYearChange(input: String) {
        _state.value = _state.value.copy(
            year = input,
            yearError = validateYear(input)
        )
        validate()
    }

    fun onNameChange(input: String) {
        _state.value = _state.value.copy(
            name = input,
            nameError = validateName(input)
        )
        validate()
    }

    fun onCvvChange(input: String) {
        _state.value = _state.value.copy(
            cvv = input,
            cvvError = validateCvv(input)
        )
        validate()
    }

    private fun validate() {
        val s = _state.value

        val valid = s.numberError == null &&
                s.monthError == null &&
                s.yearError == null &&
                s.cvvError == null &&
                s.nameError == null &&
                s.number.isNotEmpty() &&
                s.year.isNotEmpty() &&
                s.month.isNotEmpty() &&
                s.cvv.isNotEmpty() &&
                s.name.isNotEmpty()

        _state.value = s.copy(isFormValid = valid)
    }

    private fun validateCvv(cvv: String): Int? =
        if (cvv.length != 3) R.string.cvvError else null

    private fun validateMonth(month: String): Int? =
        if (month.toIntOrNull() !in 1..12) R.string.monthError else null


    private fun validateYear(year: String): Int? =
        if (year.toIntOrNull() !in 0..99) R.string.yearError else null


    private fun validateNumber(number: String): Int? {

        val digits = number.replace(" ", "")
        var finalSum = 0

        for (i in digits.indices) {
            var digit = digits[i]-'0'

            if (i % 2 != 0) finalSum += digit
            else {
                digit *= 2
                if (digit > 9) digit -= 9

                finalSum += digit
            }
        }
        return if (finalSum % 10 != 0) R.string.numberError else null
    }

    private fun validateName(name: String): Int? =
        if(name.isEmpty()) R.string.EmptyError else null
}