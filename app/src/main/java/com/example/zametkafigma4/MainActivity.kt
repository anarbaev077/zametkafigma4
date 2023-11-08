package com.example.zametkafigma4

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.zametkafigma4.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val sharedPref: NotesDataBase by lazy {
        NotesDataBase(this)
    }

    private val adapter: NotesAdapter by lazy {
        NotesAdapter(
            onDeleteNoteClick = {
                sharedPref.
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViewsAndAdapter()
        setupClickListener()
    }
    private fun setupViewsAndAdapter() {
        val listOfNotes = sharedPref.getAllNotes()
        if (listOfNotes.isNotEmpty()){
            binding.notEmptyImg.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            adapter.updateList(listOfNotes)
            binding.recyclerView.adapter = adapter
        }
    }

    private fun setupClickListener() = binding.apply {
        addNoteBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddNoteActivity::class.java))
        }
        deleteCard.setOnClickListener {
            showConfirmDeleteNoteDialog()
        }
    }
    private fun showConfirmDeleteNoteDialog() {
        val alertDialog = MaterialAlertDialogBuilder(this)
        alertDialog.setMessage("ты точно хочешь удалить?")
        alertDialog.setPositiveButton("Да") { dialog, _ ->
            deleteAllSavedNotes()
            dialog.dismiss()
        }
        alertDialog.setNegativeButton("Отменить") {dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.create().show()
    }
    private fun deleteAllSavedNotes() {
        sharedPref.deleteAllNotes()
        adapter.updateList(emptyList())
        binding.notEmptyImg.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }
}

