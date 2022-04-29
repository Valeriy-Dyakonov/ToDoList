package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityEditBinding
import com.example.myapplication.enums.OperationType
import com.example.myapplication.model.Task
import com.example.myapplication.utils.DateUtils
import com.example.myapplication.utils.LocaleUtils
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import java.util.*


class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    private lateinit var task: Task
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

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { LocaleUtils.attachBaseContext(it) })
    }

    private fun initListeners() {
        val type = type
        binding.save.setOnClickListener {
            if (canSave()) {
                val note = Task(
                    task.id,
                    binding.titleInput.text.toString(),
                    binding.categoryInput.text.toString(),
                    DateUtils.concatDateAndTime(dataText, timeText),
                    binding.contentInput.text.toString(), false
                )
                val addNote = Intent().apply {
                    putExtra("note", note)
                    putExtra("type", type.toString())
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
                titleInput.error = resources.getString(R.string.required)
            }
        }

        return binding.titleInput.error == null
    }

    private fun initData() {
        if (intent.hasExtra("note")) {
            task = intent.getSerializableExtra("note") as Task
            type = OperationType.EDIT
        } else {
            task = Task()
            type = OperationType.ADD
        }
        dataText = DateUtils.toString(task.date, DateUtils.DATE)
        timeText = DateUtils.toString(task.date, DateUtils.TIME)
        binding.apply {
            titleInput.setText(task.name)
            categoryInput.setText(task.category)
            dateInput.setText(DateUtils.toString(task.date, DateUtils.DATE))
            timeInput.setText(DateUtils.toString(task.date, DateUtils.TIME))
            contentInput.setText(task.content)

            val extraCategories = intent.getStringArrayExtra("categories")
            val categories = if (extraCategories != null) extraCategories as Array<String> else emptyArray()
            val adapter = ArrayAdapter(this@EditActivity, R.layout.list_item, categories)
            (category.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        }
    }

    private fun initPickers() {
        binding.apply {
            formTitle.text = if (type == OperationType.ADD) resources.getString(R.string.create_title) else resources.getString(R.string.edit_title)
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