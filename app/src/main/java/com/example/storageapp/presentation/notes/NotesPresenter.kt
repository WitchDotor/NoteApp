package com.example.storageapp.presentation.notes

import android.database.Observable
import android.util.Log
import com.example.storageapp.domain.use_case.AddNoteUseCase
import com.example.storageapp.domain.use_case.DeleteNoteUseCase
import com.example.storageapp.domain.use_case.GetNotesUseCase
import com.example.storageapp.presentation.model.NoteDisplayModel
import com.example.storageapp.presentation.model.NoteHolderDisplay
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NotesPresenter @Inject constructor(
    private val view: NotesObject.View,
    private val getNotesUseCase: GetNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val mapper: NoteHolderDisplay
) : NotesObject.Presenter {

    private val composite = CompositeDisposable()
    private val filtersForSort = FiltersForSort()
    private val deleteSet = mutableSetOf<String>()


    override fun loadData() {
        val disposable = getNotesUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showLoading() }
            .subscribe(
                { notesList ->
                    view.showNotes(mapper.mapNoteListToNoteDisplayList(notesList, false))
                },
                {
                    Log.e("boom", "$it in NotesPresenter method: loadData")
                    view.showError()
                },
            )
        composite.add(disposable)
    }

    override fun deleteNote() {
        val disposable = io.reactivex.rxjava3.core.Observable.fromIterable(deleteSet)
            .flatMapCompletable {
                deleteNoteUseCase.invoke(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
            .subscribe({
                view.showDeleteSucces()
            },
                {
                    view.showError()
                })
        composite.add(disposable)
    }


    override fun addToDeleteSet(noteId: String) {
        deleteSet.add(noteId)
    }

    override fun removeFromDeleteSet(noteId: String) {
        deleteSet.remove(noteId)
    }

    override fun clearDeleteSet() {
        deleteSet.clear()
    }

    override fun addNote(title: String, content: String) {
        val disposable = addNoteUseCase.invoke(title, content)
            .subscribe({
                view.navigateToNoteActivity(it)
            },
                {
                println(it)
                    view.showFailToast() })

        composite.add(disposable)
    }

    override fun sortNotes(filtration: String) {
        val disposable = getNotesUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showLoading() }
            .map { noteList ->
                val holderList = mapper.mapNoteListToNoteDisplayList(noteList, false)
                when (filtration) {
                    DECREASE -> {
                        filtersForSort.filterByAlphabetAtoZ(holderList)
                    }

                    INCREASE -> {
                        filtersForSort.filterByAlphabetZtoA(holderList)
                    }

                    else -> {
                        holderList
                    }
                }
            }
            .subscribe(
                { view.showNotes(it) },
                {
                    it.printStackTrace()
                    view.showError()
                },
            )
        composite.add(disposable)
    }

    override fun filterNotes(filter: String) {

        val disposable = getNotesUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showLoading() }
            .map { notesList ->
                notesList.filter { it.title.contains(filter, true) }
            }
            .subscribe({ notesList ->
                val holderList = mapper.mapNoteListToNoteDisplayList(notesList, false)
                view.showNotes(holderList)
            }, {
                it.printStackTrace()
                view.showError()
            })
        composite.add(disposable)
    }

    override fun onNoteClick(note: NoteDisplayModel) {
        view.navigateToNoteActivity(note.id)
    }

    override fun onLongNoteClick(note: NoteDisplayModel) {
        view.setDeleteState()
        onCheckBoxClick(note)
    }

    override fun onCheckBoxClick(note: NoteDisplayModel) {

        if (note.isChecked) {
            addToDeleteSet(note.id)
        } else {
            removeFromDeleteSet(note.id)
        }
    }

    override fun onGoBackClick() {
        view.setBasicState()
    }

    override fun dispose() {
        composite.dispose()
    }

    override fun onDeleteFubClick() {
        deleteNote()
        view.setBasicState()
    }


    companion object {

        const val INCREASE = "increase"
        const val DECREASE = "decrease"
    }
}