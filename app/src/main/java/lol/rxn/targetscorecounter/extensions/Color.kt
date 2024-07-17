package lol.rxn.targetscorecounter.extensions

import android.animation.ArgbEvaluator

typealias AndroidColor = android.graphics.Color

val GOOD_SCORE_COLOR: Int = AndroidColor.rgb(0, 200, 0)
val POOR_SCORE_COLOR: Int = AndroidColor.rgb(240, 0, 0)
private val evaluator: ArgbEvaluator = ArgbEvaluator()

object Color {
    fun gradient(
        value: Int,
        maxValue: Int,
        poorColor: Int = POOR_SCORE_COLOR,
        goodColor: Int = GOOD_SCORE_COLOR
    ): Int {
        if (value == 0 || maxValue == 0) return poorColor

        return evaluator.evaluate(
            value / maxValue.toFloat(),
            poorColor,
            goodColor
        ) as Int
    }
}