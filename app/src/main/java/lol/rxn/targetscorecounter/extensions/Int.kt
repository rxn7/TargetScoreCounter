package lol.rxn.targetscorecounter.extensions

import kotlin.math.log10

val Int.length
    get() = when {
        this == 0 -> 1
        this < 0 -> log10(-toFloat()).toInt() + 2
        else -> log10(toFloat()).toInt() + 1
    }