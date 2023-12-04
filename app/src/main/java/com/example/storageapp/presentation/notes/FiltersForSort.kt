package com.example.storageapp.presentation.notes

import com.example.storageapp.presentation.model.NoteDisplayModel

class FiltersForSort {

    fun filterByAlphabetAtoZ(list: List<NoteDisplayModel>): List<NoteDisplayModel> {
        return list.sortedBy { it.title.lowercase() }
    }

    fun filterByAlphabetZtoA(list: List<NoteDisplayModel>): List<NoteDisplayModel> {
        return list.sortedByDescending { it.title.lowercase() }
    }

}