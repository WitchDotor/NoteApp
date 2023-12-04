package com.example.storageapp.di

import android.content.Context
import com.example.storageapp.presentation.notes.NotesActivity
import com.example.storageapp.presentation.notes.NotesObject
import com.example.storageapp.presentation.notes.NotesPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)


abstract class NotesPresenterModule {
    @Binds
    abstract fun provideNotesPresenter(impl: NotesActivity): NotesObject.View

    @Binds
    abstract fun provideNoteActivity(view: NotesPresenter): NotesObject.Presenter

    companion object NotesActivityModule {
        @Provides
        fun bindActivity(@ActivityContext context: Context): NotesActivity =
            context as NotesActivity
    }

}

