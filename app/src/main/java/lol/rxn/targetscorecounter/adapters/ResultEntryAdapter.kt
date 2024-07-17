package lol.rxn.targetscorecounter.adapters

import android.content.Context
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.text.toSpannable
import lol.rxn.targetscorecounter.Colors
import lol.rxn.targetscorecounter.R
import lol.rxn.targetscorecounter.data.ResultData

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
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.result_entry, parent, false)

        if (currentEntryPosition == idx) {
            view.setBackgroundColor(Color.rgb(240, 240, 240))
        } else {
            view.setBackgroundColor(Color.WHITE)
        }

        val result: ResultData = getItem(idx)
        val scoresText: TextView = view.findViewById(R.id.scores)

        val textBuilder = SpannableStringBuilder()
        var total = 0 // NOTE: Total is not calculated using result.getTotalScore() to not iterate twice
        for (score in result.scores) {
            total += score

            textBuilder.append("$score")
            textBuilder.append(' ')

            if(score < 10) {
                textBuilder.append(' ')
            }

            val colorSpan = ForegroundColorSpan(Colors.getGradient(score, 10))

            val len: Int = textBuilder.count()
            textBuilder.setSpan(colorSpan, len - 3, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        textBuilder.trimEnd()

        scoresText.text = textBuilder.toSpannable()

        val totalScoreText: TextView = view.findViewById(R.id.score_total)
        totalScoreText.setTextColor(Colors.getGradient(total, result.getMaxPossibleScore()))

        totalScoreText.text = "$total"

        return view
    }
}