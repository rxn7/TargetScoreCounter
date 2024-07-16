package lol.rxn.targetscorecounter

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import lol.rxn.targetscorecounter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var results: ArrayList<ResultData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initKeyboard()

        for(i in 0..100)
            addRandomResult()

        println(binding.resultsList.count)

        binding.resultsList.adapter = ResultEntryAdapter(this, results)
    }

    private fun addRandomResult() {
        val scores: ArrayList<Int> = ArrayList()
        for(i in 1..10) {
            val score: Int = (1..10).random()
            scores.add(score)
        }
        val result = ResultData(scores.toTypedArray<Int>())
        results.add(result)
    }

    private fun initKeyboard() {
        val ids: Array<Int> = arrayOf(R.id.keyboard_1, R.id.keyboard_2, R.id.keyboard_3, R.id.keyboard_4, R.id.keyboard_5, R.id.keyboard_6, R.id.keyboard_7, R.id.keyboard_8, R.id.keyboard_9, R.id.keyboard_10)
        for(i in 1..10) {
            val btn: Button = findViewById<Button>(ids[i-1])
            btn.setOnClickListener {
                addScoreToCurrentEntry(i)
            }
        }
    }

    private fun addScoreToCurrentEntry(value: Int) {
    }
}