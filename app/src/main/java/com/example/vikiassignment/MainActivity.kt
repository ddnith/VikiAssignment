package com.example.vikiassignment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.vikiassignment.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    lateinit var fromInput: EditText
    lateinit var toOutput: EditText
    lateinit var convertButton: Button
    lateinit var fromDropdown: Spinner
    lateinit var toDropdown: Spinner
    lateinit var progressBar: ProgressBar
    lateinit var fromCurrency: String
    lateinit var toCurrency: String

    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setUpViewModel()
        setUpInitialData()
    }

    private fun setUpInitialData() {
        fromInput.setText(mainViewModel.fromCurrencyValue)
        toOutput.setText(mainViewModel.toCurrencyValue)
    }

    private fun setUpViewModel() {
        progressBar.visibility = VISIBLE
        mainViewModel.getLatestRates()
        mainViewModel.observeLatestRatesLiveData().observe(this, Observer { latest -> if (latest != null) {
            progressBar.visibility = GONE
            val fromAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_spinner_item, latest.conversionRates.keys.toList())
            fromDropdown.adapter = fromAdapter

            val toAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_spinner_item, latest.conversionRates.keys.toList())
            toDropdown.adapter = toAdapter
            fromDropdown.setSelection(mainViewModel.fromCurrencyPosition)
            toDropdown.setSelection(mainViewModel.toCurrencyPosition)
        }
        })
        mainViewModel.observePairRateLiveData().observe(this, Observer {
                pairAmount ->
            progressBar.visibility = GONE
            toOutput.setText(pairAmount.conversionResult.toString())
        })
    }

    private fun setupUI() {
        fromInput = findViewById(R.id.fromCurrency)
        toOutput = findViewById(R.id.toCurrency)
        convertButton = findViewById(R.id.convertButton)
        fromDropdown = findViewById(R.id.fromDropdown)
        toDropdown = findViewById(R.id.toDropdown)
        progressBar = findViewById(R.id.progressBar)
        configureFromEditText()
        configureToEditText()

        setUpListeners()
    }

    private fun configureToEditText() {
        toOutput.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mainViewModel.toCurrencyValue = s.toString()
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    private fun configureFromEditText() {
        fromInput.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mainViewModel.fromCurrencyValue = s.toString()
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    private fun setUpListeners() {

        fromDropdown.onItemSelectedListener = FromDropdown()
        toDropdown.onItemSelectedListener = ToDropdown()

        convertButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!isInternetAvailable(this@MainActivity)) {
                    Toast.makeText(
                        this@MainActivity,
                        "Host unreachable, check your internet connection and try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                if (fromCurrency === toCurrency) {
                    Toast.makeText(
                        this@MainActivity,
                        "from and to values are same.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                } else if (fromInput.text.toString().isEmpty()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Please enter a value to convert.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                } else {
                    progressBar.visibility = VISIBLE
                    mainViewModel.getPairAmount(fromCurrency, toCurrency, fromInput.text.toString())
                }
            }
        })
    }

    internal inner class FromDropdown : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            fromCurrency = parent.getItemAtPosition(position).toString()
            mainViewModel.fromCurrencyPosition = position
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    internal inner class ToDropdown : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            toCurrency = parent.getItemAtPosition(position).toString()
            mainViewModel.toCurrencyPosition = position
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
}