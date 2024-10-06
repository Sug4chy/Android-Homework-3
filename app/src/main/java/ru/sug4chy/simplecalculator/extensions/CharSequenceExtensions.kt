package ru.sug4chy.simplecalculator.extensions

fun CharSequence.containsMathOperations(): Boolean =
    this.contains(Regex("[+\\-*/]"))

fun CharSequence.startsWithDigit(): Boolean =
    this.first().isDigit()