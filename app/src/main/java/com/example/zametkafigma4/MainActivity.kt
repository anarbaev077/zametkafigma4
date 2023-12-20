package com.example.zametkafigma4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import com.example.zametkafigma4.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val sharedPref: NotesDataBase by lazy {
        NotesDataBase(this)
    }

    private val adapter: NotesAdapter by lazy {
        NotesAdapter(
            onDeleteNoteClick = {index ->
                sharedPref.deleteNoteAtIndex(index)
                setupViewsAndAdapter()
            }, reportToDetails = {}
        )
    }
    private var notesList: List<NotesModel> = emptyList()

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
        } else {
            binding.notEmptyImg.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }
        notesList = listOfNotes
        adapter.updateList(listOfNotes)
        binding.recyclerView.adapter = adapter
    }
    private fun filterNotes(title: String) {
        val filterNote = notesList.filter { name ->
            name.noteTitle.contains(title, ignoreCase = false)
        }
        adapter.updateList(filterNote)
    }

    private fun setupClickListener() = binding.apply {
        addNoteBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddNoteActivity::class.java))
        }
        deleteCard.setOnClickListener {
            showConfirmDeleteNoteDialog()
        }
        noteSearchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(text: String?): Boolean {
                text?.let {
                    filterNotes(it)
                }
                return true
            }
        })
    }
    private fun showConfirmDeleteNoteDialog() {
        val alertDialog = MaterialAlertDialogBuilder(this)
        alertDialog.setMessage(getString(R.string.do_you_want_to_delete_tv))
        alertDialog.setPositiveButton(getString(R.string.ok)) { dialog, _ ->
            deleteAllSavedNotes()
            dialog.dismiss()
        }
        alertDialog.setNegativeButton(getString(R.string.cancel_tv)) { dialog, _ ->
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
    companion object {
        const val NAVIGATE_TO_DETAILS = "NAVIGATE_TO_DETAILS"
    }
}

