package com.example.notesfeature.internal.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.backend.BackendCommunication
import com.example.notesfeature.internal.notelist.service.NoteService
import com.example.notesfeature.internal.notelist.view.NoteListViewContainer

internal class NoteListViewModel(backendCommunication: BackendCommunication) : ViewModel() {

    /**
     * ADR # 4. MVP: Store Presenter inside ViewModel
     */
    val presenter = NoteListPresenter(
        NoteListViewContainer(),
        NoteListNavigation(),
        NoteService(backendCommunication),
        viewModelScope
    )

    init {
        presenter.start()
    }

    override fun onCleared() {
        super.onCleared()
        presenter.clear()
    }

    class Factory(private val backendCommunication: BackendCommunication) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            NoteListViewModel(backendCommunication) as T
    }
}
