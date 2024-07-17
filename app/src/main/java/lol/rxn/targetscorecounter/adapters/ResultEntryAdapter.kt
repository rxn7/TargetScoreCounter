package lol.rxn.targetscorecounter.adapters

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.text.toSpannable
import lol.rxn.targetscorecounter.R
import lol.rxn.targetscorecounter.data.ResultData
import lol.rxn.targetscorecounter.extensions.AndroidColor
import lol.rxn.targetscorecounter.extensions.Color
import lol.rxn.targetscorecounter.extensions.GOOD_SCORE_COLOR
import lol.rxn.targetscorecounter.extensions.length

class ResultEntryAdapter(private val ctx: Context, private val results: ArrayList<ResultData>) : BaseAdapter() {
    var currentEntryPosition: Int = 0

    override fun getCount(): Int {
        return results.size
    }

    override fun getItem(idx: Int): ResultData {
        return results[idx]
    }

    override fun getItemId(idx: Int): Long {
        return idx.toLong()
    }

    override fun getView(idx: Int, v: View?, parent: ViewGroup?): View {
        val view: View = v ?: LayoutInflater.from(ctx).inflate(R.layout.result_entry, parent, false)
        view.setBackgroundColor(
            if (currentEntryPosition == idx) AndroidColor.rgb(
                240,
                240,
                240
            ) else AndroidColor.WHITE
        )

        val result: ResultData = getItem(idx)
        val maxPossibleScore: Int = result.getMaxPossibleScore()

        val textBuilder = SpannableStringBuilder()
        var total = 0 // NOTE: Total is not calculated using result.getTotalScore() to not iterate twice
        for (score in result.scores) {
            total += score

            textBuilder.append("$score")
            textBuilder.append(' ')

            if (score < 10) textBuilder.append(' ')

            val colorSpan = ForegroundColorSpan(Color.gradient(score, 10))

            val len: Int = textBuilder.count()
            textBuilder.setSpan(colorSpan, len - 3, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        textBuilder.trimEnd()

        val scoresText: TextView = view.findViewById(R.id.scores)
        scoresText.text = textBuilder.toSpannable()

        textBuilder.clear()
        textBuilder.clearSpans()

        textBuilder.append("$total")
        textBuilder.setSpan(
            ForegroundColorSpan(
                if (maxPossibleScore > 0) Color.gradient(
                    total,
                    maxPossibleScore
                ) else AndroidColor.BLACK
            ), 0, textBuilder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textBuilder.append(" / ")

        textBuilder.append("$maxPossibleScore")
        textBuilder.setSpan(
            ForegroundColorSpan(GOOD_SCORE_COLOR),
            textBuilder.length - maxPossibleScore.length,
            textBuilder.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val totalScoreText: TextView = view.findViewById(R.id.score_total)
        totalScoreText.text = textBuilder.toSpannable()

        return view
    }
}