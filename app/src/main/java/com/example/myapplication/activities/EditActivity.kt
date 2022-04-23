package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityEditBinding
import com.example.myapplication.model.Note
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import java.util.*


class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    private var note: Note? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        note = if (intent.hasExtra("note")) intent.getSerializableExtra("note") as Note else null
        binding = ActivityEditBinding.inflate(layoutInflater)
        binding.apply {
            date.setEndIconOnClickListener {
                val datePicker =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .build()
                datePicker.show(supportFragmentManager, "tag")
            }
            time.setEndIconOnClickListener {
                val timePicker = MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setInputMode(INPUT_MODE_KEYBOARD)
                    .build()
                timePicker.show(supportFragmentManager, "tag");
            }
            titleInput.setText(note?.name ?: "")
            contentInput.setText(note?.content ?: "")
        }
        initListeners()
        setContentView(binding.root)
    }

    private fun initListeners() {
        binding.save.setOnClickListener {
            val note = Note(binding.titleInput.text.toString(), Date(), binding.contentInput.text.toString(), false);
            val addNote = Intent().apply {
                putExtra("note", note)
            }
            setResult(RESULT_OK, addNote)
            finish()
        }
    }
}