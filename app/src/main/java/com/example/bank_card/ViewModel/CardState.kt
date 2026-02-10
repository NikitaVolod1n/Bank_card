package com.example.bank_card.ViewModel

data class CardState(
    val number: String = "",
    val month: String = "",
    val year: String = "",
    val name: String = "",
    val cvv: String = "",

    val numberError: Int? = null,
    val monthError: Int? = null,
    val yearError: Int? = null,
    val nameError: Int? = null,
    val cvvError: Int? = null,

    val isFormValid: Boolean = false
)
