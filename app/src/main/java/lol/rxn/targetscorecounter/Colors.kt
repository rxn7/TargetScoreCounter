package lol.rxn.targetscorecounter

import android.animation.ArgbEvaluator
import android.graphics.Color

private val GOOD_SCORE_COLOR: Int = Color.rgb(0, 200, 0)
private val POOR_SCORE_COLOR: Int = Color.rgb(240, 0, 0)

object Colors {
    private val evaluator: ArgbEvaluator = ArgbEvaluator()

    fun getGradient(
        score: Int,
        maxScore: Int,
        poorColor: Int = POOR_SCORE_COLOR,
        goodColor: Int = GOOD_SCORE_COLOR
    ): Int {
        if(score == 0 || maxScore == 0) {
            return poorColor
        }

        return evaluator.evaluate(
            score / maxScore.toFloat(),
            poorColor,
            goodColor
        ) as Int
    }
}
