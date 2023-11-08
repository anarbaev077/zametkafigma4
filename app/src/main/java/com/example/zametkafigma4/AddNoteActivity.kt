package com.example.zametkafigma4

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zametkafigma4.databinding.ActivityAddNoteBinding
import com.example.zametkafigma4.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class AddNoteActivity : AppCompatActivity() {

    private val binding: ActivityAddNoteBinding by lazy {
        ActivityAddNoteBinding.inflate(layoutInflater)
    }
    private val sharedPref: NotesDataBase by lazy {
        NotesDataBase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.saveCard.setOnClickListener {
            saveNotes()
        }
        binding.backCard.setOnClickListener {
            finish()
        }
    }

    private fun saveNotes() = binding.apply {
        if (titleEt.text?.isNotEmpty() == true && overviewEt.text?.isNotEmpty() == true) {
            sharedPref.saveNote(
                NotesModel(
                    noteTitle = titleEt.text.toString(),
                    noteDescription = overviewEt.text.toString(),
                )
            )
            startActivity(Intent(this@AddNoteActivity, MainActivity::class.java))
        } else showToastMessage("Заполните все поля")
    }

    private fun showToastMessage(massage: String) {
        Snackbar.make(
            binding.root,
            massage,
            Snackbar.LENGTH_SHORT,
        ).show()
    }
}