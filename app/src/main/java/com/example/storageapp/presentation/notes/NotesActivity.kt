package com.example.storageapp.presentation.notes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.example.storageapp.R
import com.example.storageapp.databinding.ActivityNotesBinding
import com.example.storageapp.presentation.model.NoteDisplayModel
import com.example.storageapp.presentation.note.NoteActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class NotesActivity :
    AppCompatActivity(), NotesObject.View {

    @Inject
    lateinit var presenter: NotesObject.Presenter

    private val binding by lazy { ActivityNotesBinding.inflate(layoutInflater) }
    private val adapter by lazy {
        NotesAdapter(
            onNoteClick = { presenter.onNoteClick(it) },
            onLongNoteClick = { presenter.onLongNoteClick(it) },
            onCheckBoxClick = { presenter.onCheckBoxClick(it) }
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerNotes.adapter = adapter
        presenter.loadData()

        binding.toolBar.inflateMenu(R.menu.notes_menu)

        binding.toolBar.setOnMenuItemClickListener {
            when (it) {
                binding.toolBar.menu.findItem(R.id.menu_delete) -> {
                    setDeleteState()
                    true
                }

                binding.toolBar.menu.findItem(R.id.menu_search) -> {
                    setSearchState()
                    true
                }

                binding.toolBar.menu.findItem(R.id.menu_sort_alphabet_a_to_z) -> {
                    presenter.sortNotes(DECREASE)
                    true
                }

                binding.toolBar.menu.findItem(R.id.menu_sort_alphabet_z_to_a) -> {
                    presenter.sortNotes(INCREASE)
                    true
                }

                else -> {
                    true
                }
            }
        }

        binding.ibtnGoBack.setOnClickListener {
            setBasicState()
        }

        binding.fabDelete.setOnClickListener {
            presenter.onDeleteFubClick()
        }

        binding.fab.setOnClickListener {
            presenter.addNote(TITLE, CONTENT)
        }

        binding.etSearch.doAfterTextChanged { presenter.filterNotes(it.toString()) }
    }

    companion object {

        const val TITLE = "New title"
        const val CONTENT = "new content"
        const val INCREASE = "increase"
        const val DECREASE = "decrease"

        fun navigateTo(thisContext: Context): Intent {
            return Intent(thisContext, NoteActivity::class.java)
        }

        const val NOTEID = "note id"
    }

    override fun onDestroy() {
        presenter.dispose()
        super.onDestroy()

    }

    override fun showFailToast() {
        Toast.makeText(this, getString(R.string.somth_went_wrong), Toast.LENGTH_LONG).show()
    }

    override fun navigateToNoteActivity(noteId: String) {
        val intent = navigateTo(this).apply {
            putExtra(NOTEID, noteId)
        }
        startActivity(intent)
    }

    override fun setDeleteState() {
        adapter.setDeleteState(true)
        binding.fabDelete.isVisible = true
        binding.fab.isVisible = false
        binding.ibtnGoBack.isVisible = true
    }

    override fun setSearchState() {
        binding.etSearch.isVisible = true
        binding.ibtnGoBack.isVisible = true
    }

    override fun setBasicState() {
        presenter.clearDeleteSet()
        adapter.setDeleteState(false)
        binding.fabDelete.isVisible = false
        binding.fab.isVisible = true
        binding.ibtnGoBack.isVisible = false
        binding.etSearch.isVisible = false
        presenter.loadData()
    }

    override fun showLoading() {
        // changeState(State.Loading)
        binding.progressLoading.isVisible = true
        binding.textError.isVisible = false
        binding.recyclerNotes.isVisible = false
    }

    override fun showNotes(
        notes: List<NoteDisplayModel>
    ) {
        // changeState(State.Loaded)
        binding.progressLoading.isVisible = false
        binding.textError.isVisible = false
        binding.recyclerNotes.isVisible = true
        adapter.submitList(notes)
    }

    override fun showError() {
        // changeState(State.Error)
        binding.progressLoading.isVisible = false
        binding.textError.isVisible = true
        binding.recyclerNotes.isVisible = false
    }

    override fun showDeleteSucces() {
        Toast.makeText(this, "Delete succed", Toast.LENGTH_SHORT).show()
    }


    private fun changeState(state: State) {
        binding.progressLoading.isVisible = state == State.Loading
        binding.textError.isVisible = state == State.Error
        binding.recyclerNotes.isVisible = state == State.Loaded
    }
}

enum class State {
    Loading,
    Loaded,
    Error
}
