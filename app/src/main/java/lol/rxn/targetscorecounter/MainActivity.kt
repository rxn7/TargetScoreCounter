package lol.rxn.targetscorecounter

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import lol.rxn.targetscorecounter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var resultsAdapter: ResultEntryAdapter
    private var results: ArrayList<ResultData> = ArrayList()
    private lateinit var currentEntry: ResultData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initKeyboard()

        currentEntry = ResultData(arrayListOf())
        results.add(currentEntry)

        resultsAdapter = ResultEntryAdapter(this, results)
        binding.resultsList.adapter = resultsAdapter
    }

    private fun initKeyboard() {
        val buttons: Array<Button> = arrayOf(binding.keyboard1, binding.keyboard2, binding.keyboard3, binding.keyboard4, binding.keyboard5, binding.keyboard6, binding.keyboard7, binding.keyboard8, binding.keyboard9, binding.keyboard10)
        for(i in 1..10) {
            val btn: Button = buttons[i - 1]
            btn.setTextColor(Colors.getGradient(i, 10))
            btn.setOnClickListener { addScoreToCurrentEntry(i) }
        }

        binding.keyboardRemove.setOnClickListener { removeLastScore() }
        binding.keyboardRemove.setOnLongClickListener { removeLastResultEntry() }
        binding.keyboardSubmit.setOnClickListener { submitCurrentEntry() }
    }

    private fun addScoreToCurrentEntry(value: Int) {
        if(results.isEmpty()) {
            results.add(ResultData(arrayListOf()))
        }

        val currentEntry: ResultData = results.last()
        currentEntry.scores.add(value)

        resultsAdapter.notifyDataSetChanged()
        focusOnCurrentEntry()
    }

    private fun removeLastScore() {
        currentEntry.scores.removeLastOrNull()

        if(currentEntry.scores.isEmpty() && results.count() > 1) {
            results.remove(currentEntry)
            currentEntry = results.last()
        }

        resultsAdapter.notifyDataSetChanged()
        focusOnCurrentEntry()
    }

    private fun removeLastResultEntry(): Boolean {
        if(results.count() == 1) {
            currentEntry.scores.clear()
        } else {
            results.remove(currentEntry)
            currentEntry = results.last()
        }

        resultsAdapter.notifyDataSetChanged()
        focusOnCurrentEntry()
        return true
    }

    private fun submitCurrentEntry() {
        currentEntry = ResultData(arrayListOf())
        results.add(currentEntry)
        resultsAdapter.notifyDataSetChanged()
        focusOnCurrentEntry()
    }

    private fun focusOnCurrentEntry() {
        binding.resultsList.setSelection(resultsAdapter.count - 1)
    }
}