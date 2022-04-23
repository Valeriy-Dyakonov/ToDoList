package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.widget.addTextChangedListener
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityEditBinding
import com.example.myapplication.enums.OperationType
import com.example.myapplication.model.Note
import com.example.myapplication.utils.DateUtils
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import java.util.*


class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    private lateinit var note: Note
    private lateinit var type: OperationType
    private var dataText: String = ""
    private var timeText: String = ""

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        initData()
        initPickers()
        initListeners()
        setContentView(binding.root)
    }

    private fun initListeners() {
        val type = type
        binding.save.setOnClickListener {
            if (canSave()) {
                val note = Note(
                    note.id,
                    binding.titleInput.text.toString(),
                    binding.categoryInput.text.toString(),
                    DateUtils.concatDateAndTime(dataText, timeText),
                    binding.contentInput.text.toString(), false
                )
                val addNote = Intent().apply {
                    putExtra("note", note)
                    putExtra("type",type.toString())
                }
                setResult(RESULT_OK, addNote)
                finish()
            }
        }

        binding.apply {
            titleInput.addTextChangedListener {
                if (binding.titleInput.text.toString().isNotEmpty()) {
                    binding.titleInput.error = null
                }
            }
        }
    }

    private fun canSave(): Boolean {
        binding.apply {
            if (titleInput.text.toString().isEmpty()) {
                titleInput.error = "Required"
            }
        }

        return binding.titleInput.error == null
    }

    private fun initData() {
        if (intent.hasExtra("note")) {
            note = intent.getSerializableExtra("note") as Note
            type = OperationType.EDIT
        } else {
            note = Note()
            type = OperationType.ADD
        }
        dataText = DateUtils.toString(note.date, DateUtils.DATE)
        timeText = DateUtils.toString(note.date, DateUtils.TIME)
        binding.apply {
            titleInput.setText(note.name)
            categoryInput.setText(note.category)
            dateInput.setText(DateUtils.toString(note.date, DateUtils.DATE))
            timeInput.setText(DateUtils.toString(note.date, DateUtils.TIME))
            contentInput.setText(note.content)

            val categories = intent.getStringArrayExtra("categories") as Array<String>
            val adapter = ArrayAdapter(this@EditActivity, R.layout.list_item, categories)
            (category.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        }
    }

    private fun initPickers() {
        binding.apply {
            formTitle.text = if (type == OperationType.ADD) "Create new task" else "Edit task"
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .build()
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setInputMode(INPUT_MODE_KEYBOARD)
                .build()

            datePicker.addOnPositiveButtonClickListener {
                dataText = DateUtils.toString(Date(it), DateUtils.DATE)
                dateInput.setText(dataText)
            }

            timePicker.addOnPositiveButtonClickListener {
                val hours = timePicker.hour
                val minutes = timePicker.minute
                val textH = if (hours < 10) "0$hours" else "$hours"
                val textM = if (minutes < 10) "0$minutes" else "$minutes"
                timeText = "$textH:$textM"
                timeInput.setText(timeText)
            }

            date.setEndIconOnClickListener {
                datePicker.show(supportFragmentManager, "tag")
            }
            time.setEndIconOnClickListener {
                timePicker.show(supportFragmentManager, "tag")
            }
        }
    }
}