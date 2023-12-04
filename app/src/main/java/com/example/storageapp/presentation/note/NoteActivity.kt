package com.example.storageapp.presentation.note

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.storageapp.R
import com.example.storageapp.databinding.ActivityNoteBinding
import com.example.storageapp.presentation.notes.NotesActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class NoteActivity : AppCompatActivity(), NoteObject.View {

    private val binding by lazy { ActivityNoteBinding.inflate(layoutInflater) }

    @Inject
    lateinit var presenter: NoteObject.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        presenter.getNote(intent.getStringExtra(NOTEID).toString())


        binding.ibtnGoBack.setOnClickListener {
            presenter.updateNote(binding.title.text.toString(), binding.content.text.toString())
            navigateToNotesActivity()
        }

        binding.fab.setOnClickListener {
            presenter.updateNote(binding.title.text.toString(), binding.content.text.toString())
        }
    }

    companion object {
        fun navigateTo(thisContext: Context): Intent {
            return Intent(thisContext, NotesActivity::class.java)
        }

        const val NOTEID = "note id"
    }

    override fun onDestroy() {
        presenter.dispose()
        super.onDestroy()
    }

    override fun showNote() {
        binding.progressBar.isVisible = false
        binding.title.isVisible = true
        binding.content.isVisible = true
    }

    override fun showError() {
        Toast.makeText(this, getString(R.string.somth_went_wrong), Toast.LENGTH_LONG).show()
        binding.progressBar.isVisible = false
    }

    override fun showUpdateError() {
        Toast.makeText(this, getString(R.string.UpdateError), Toast.LENGTH_LONG).show()
        binding.progressBar.isVisible = false
    }

    override fun showGetError() {
        Toast.makeText(this, getString(R.string.GetNoteError), Toast.LENGTH_LONG).show()
        binding.progressBar.isVisible = false
    }

    override fun showUpdateSucces() {
        Toast.makeText(this, getString(R.string.UpdateSucces), Toast.LENGTH_SHORT).show()
        binding.progressBar.isVisible = false
    }

    override fun initializeNote(title: String, content: String) {
        showNote()
        binding.title.setText(title)
        binding.content.setText(content)
    }


    override fun showLoading() {
        binding.progressBar.isVisible = true
        binding.title.isVisible = false
        binding.content.isVisible = false
    }


    override fun navigateToNotesActivity() {
        startActivity(navigateTo(this))
    }
}