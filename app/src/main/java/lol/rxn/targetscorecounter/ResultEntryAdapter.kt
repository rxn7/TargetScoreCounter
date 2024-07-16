package lol.rxn.targetscorecounter

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

class ResultEntryAdapter(private val ctx: Context, private val results: ArrayList<ResultData>) : BaseAdapter() {
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
        val result: ResultData = getItem(idx)
        val scoresText: TextView = view.findViewById(R.id.scores)

        val textBuilder = SpannableStringBuilder()
        var total = 0
        for (score in result.scores) {
            total += score

            textBuilder.append("$score")
            textBuilder.append(' ')

            if(score < 10) {
                textBuilder.append(' ')
            }

            val colorSpan: ForegroundColorSpan = when {
                score >= 8 -> ForegroundColorSpan(Color.rgb(0, 200, 0))
                score >= 5 -> ForegroundColorSpan(Color.rgb(255, 200, 0))
                else -> ForegroundColorSpan(Color.rgb(240, 0, 0))
            }

            val len: Int = textBuilder.count()
            textBuilder.setSpan(colorSpan, len - 3, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        textBuilder.trimEnd()

        scoresText.text = textBuilder.toSpannable()

        val totalScoreText: TextView = view.findViewById(R.id.score_total)
        totalScoreText.text = "$total"

        return view
    }
}