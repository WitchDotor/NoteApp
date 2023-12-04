package com.example.storageapp.presentation.note

import com.example.storageapp.domain.use_case.GetNoteUseCase
import com.example.storageapp.domain.use_case.UpdateNoteUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NotePresenter @Inject constructor(
    val view: NoteObject.View,
    val getNoteUseCase: GetNoteUseCase,
    val updateNoteUseCase: UpdateNoteUseCase
) :
    NoteObject.Presenter {
    var compositeDisposable = CompositeDisposable()
    private var id="0"

    override fun getNote(noteId: String) {
        val disposable = getNoteUseCase.invoke(noteId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showLoading() }
            .subscribe(
                {
                    id = it.id
                    view.initializeNote(it.title, it.content)
                },
                {
                    it.printStackTrace()
                    view.showGetError()
                },
            )
        compositeDisposable.add(disposable)
    }


    override fun updateNote(title: String, content: String) {
        val disposable = updateNoteUseCase.invoke(id, title, content)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { view.showUpdateSucces()},
                { view.showUpdateError()})

        compositeDisposable.add(disposable)
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }
}