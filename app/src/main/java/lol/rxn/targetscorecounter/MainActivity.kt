package lol.rxn.targetscorecounter

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import lol.rxn.targetscorecounter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var resultsAdapter: ResultEntryAdapter
    private var results: ArrayList<ResultData> = ArrayList()

    private var currentEntry: ResultData = ResultData(arrayListOf())
    private var currentEntryPosition: Int
        get() = results.indexOf(currentEntry)
        set(idx) {
            resultsAdapter.currentEntryPosition = idx
            currentEntry = results[idx]
            binding.resultsList.invalidateViews()
            binding.resultsList.setSelection(idx)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.resultsList.setOnItemClickListener { _: AdapterView<*>, _: View, idx: Int, _: Long ->
            currentEntryPosition = idx
        }

        binding.resultsList.setOnItemLongClickListener { _: AdapterView<*>, _: View, idx: Int, _: Long ->
            removeEntry(idx)
            true
        }

        initKeyboard()

        results.add(currentEntry)
        resultsAdapter = ResultEntryAdapter(this, results)
        binding.resultsList.adapter = resultsAdapter

        currentEntryPosition = 0
    }

    private fun initKeyboard() {
        val buttons: Array<Button> = arrayOf(binding.keyboard1, binding.keyboard2, binding.keyboard3, binding.keyboard4, binding.keyboard5, binding.keyboard6, binding.keyboard7, binding.keyboard8, binding.keyboard9, binding.keyboard10)
        for(i in 1..10) {
            val btn: Button = buttons[i - 1]
            btn.setTextColor(Colors.getGradient(i, 10))
            btn.setOnClickListener { addScoreToCurrentEntry(i) }
        }

        binding.keyboardRemove.setOnClickListener { removeLastScore() }
        binding.keyboardSubmit.setOnClickListener { submitCurrentEntry() }
    }

    private fun addScoreToCurrentEntry(value: Int) {
        currentEntry.scores.add(value)
        resultsAdapter.notifyDataSetChanged()
    }

    private fun removeLastScore() {
        currentEntry.scores.removeLastOrNull()

        if(currentEntry.scores.isEmpty() && results.count() > 1) {
            results.remove(currentEntry)
            currentEntryPosition = results.lastIndex
        }

        resultsAdapter.notifyDataSetChanged()
    }

    private fun removeEntry(position: Int) {
        if(results.count() == 1) {
            currentEntry.scores.clear()
        } else {
            results.removeAt(position)
            resultsAdapter.notifyDataSetChanged()
            currentEntryPosition = position.coerceIn(0..results.lastIndex)
        }
    }

    private fun submitCurrentEntry() {
        results.add(ResultData(arrayListOf()))
        currentEntryPosition = results.lastIndex

        resultsAdapter.notifyDataSetChanged()
    }
}