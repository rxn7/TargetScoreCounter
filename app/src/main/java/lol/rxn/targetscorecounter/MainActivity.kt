package lol.rxn.targetscorecounter

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import lol.rxn.targetscorecounter.adapters.ResultEntryAdapter
import lol.rxn.targetscorecounter.data.ResultData
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
            binding.resultsList.invalidateViews() // TODO: Invalidating all views just to change the background color is slow
            binding.resultsList.setSelection(idx)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        initBinding()
        initResultsAdapter()
        initResultsList()
        initKeyboard()
    }

    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initResultsList() {
        binding.resultsList.setOnItemClickListener { _: AdapterView<*>, _: View, idx: Int, _: Long ->
            currentEntryPosition = idx
        }

        binding.resultsList.setOnItemLongClickListener { _: AdapterView<*>, _: View, idx: Int, _: Long ->
            showRemoveEntryDialog(idx)
            true
        }
    }

    private fun initResultsAdapter() {
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

    private fun removeEntry(idx: Int) {
        if(results.count() == 1) {
            currentEntry.scores.clear()
        } else {
            results.removeAt(idx)
            resultsAdapter.notifyDataSetChanged()
            currentEntryPosition = idx.coerceIn(0..results.lastIndex)
        }
    }

    private fun submitCurrentEntry() {
        results.add(ResultData(arrayListOf()))
        currentEntryPosition = results.lastIndex

        resultsAdapter.notifyDataSetChanged()
    }

    private fun showRemoveEntryDialog(idx: Int) {
        currentEntryPosition = idx

        AlertDialog.Builder(this)
            .setTitle("Delete entry?")
            .setMessage("Are you sure you want to delete this entry?")
            .setPositiveButton(android.R.string.yes) { _: DialogInterface, _: Int ->
                removeEntry(idx)
            }
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
}