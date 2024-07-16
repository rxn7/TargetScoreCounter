package lol.rxn.targetscorecounter

import android.animation.ArgbEvaluator
import android.graphics.Color

object Colors {
    private val evaluator: ArgbEvaluator = ArgbEvaluator()
    val GOOD_SCORE_COLOR: Int = Color.rgb(0, 200, 0)
    val POOR_SCORE_COLOR: Int = Color.rgb(240, 0, 0)

    fun getGradient(score: Int, maxScore: Int): Int {
        if(score == 0 || maxScore == 0) {
            return POOR_SCORE_COLOR
        }

        return evaluator.evaluate(
            score / maxScore.toFloat(),
            POOR_SCORE_COLOR,
            GOOD_SCORE_COLOR
        ) as Int
    }
}
