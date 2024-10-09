package com.example.myapplication_2.presentation.view.activity

import android.app.DatePickerDialog
import android.graphics.Color
import java.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication_2.R
import com.example.myapplication_2.data.model.MatchType

class TournamentInputActivity : AppCompatActivity() {

    private lateinit var nextButton: Button
    private lateinit var tournamentNameEditText: EditText
    private lateinit var datePickerButton: Button
    private lateinit var matchTypeSpinner: Spinner
    private lateinit var selectedDate: String
    private var selectedMatchType: MatchType = MatchType.SINGLES // 初期状態はシングルス
    private var isDateSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament_input)

        nextButton = findViewById(R.id.nextButton)
        tournamentNameEditText = findViewById(R.id.tournamentNameEditText)
        datePickerButton = findViewById(R.id.datePickerButton)
        matchTypeSpinner = findViewById(R.id.matchTypeSpinner)

        // Initially disable the button
        nextButton.isEnabled = false
        nextButton.setBackgroundColor(Color.GRAY)

        // TextWatcher for tournament name
        tournamentNameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateInputs()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Enterキーで入力完了し、キーボードを閉じるリスナー
        tournamentNameEditText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                if (tournamentNameEditText.text.toString().trim().isEmpty()) {
                    tournamentNameEditText.text.clear()
                }
                tournamentNameEditText.clearFocus() // EditTextのフォーカスを外す
                hideKeyboard()  // キーボードを閉じる
                true
            } else {
                false
            }
        }

        // Set up DatePickerDialog
        datePickerButton.setOnClickListener {
            showDatePickerDialog()
        }

        // Set up Spinner for MatchType
        val matchTypes = arrayOf(
            MatchType.SINGLES.getValue(),
            MatchType.DOUBLES.getValue(),
            MatchType.TEAM.getValue()
        )
        matchTypeSpinner.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, matchTypes
        )

        // Handle form submission
        findViewById<Button>(R.id.nextButton).setOnClickListener {
            val tournamentName = tournamentNameEditText.text.toString()
            selectedMatchType = matchTypeSpinner.selectedItem as MatchType

            // Navigate to match input screen, passing tournament data
//            val intent = Intent(this, MatchInputActivity::class.java).apply {
//                putExtra("tournamentName", tournamentName)
//                putExtra("date", selectedDate)
//                putExtra("matchType", selectedMatchType)
//            }
//            startActivity(intent)
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                datePickerButton.text = selectedDate
                isDateSelected = true
                validateInputs()
            }, year, month, day)

        datePickerDialog.show()
    }

    private fun validateInputs() {
        val isTournamentNameFilled = tournamentNameEditText.text.toString().trim().isNotEmpty()
        if (isTournamentNameFilled && isDateSelected) {
            nextButton.isEnabled = true
            nextButton.setBackgroundColor(Color.BLUE) // Enable button color (Blue, for example)
        } else {
            nextButton.isEnabled = false
            nextButton.setBackgroundColor(Color.GRAY) // Disable button color
        }
    }

    // キーボードを閉じるメソッド
    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(tournamentNameEditText.windowToken, 0)
    }
}
