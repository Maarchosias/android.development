package com.example.noci.notes.input

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.noci.database.Note
import com.example.noci.database.NoteDatabase
import com.example.noci.database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InputViewModel(application: Application) : AndroidViewModel(application) {

    private val _insertInitializer = MutableLiveData<Boolean>()
    val insertInitializer: LiveData<Boolean>
        get() = _insertInitializer

    private val _insertDateInitializer = MutableLiveData<Boolean>()
    val insertDateInitializer: LiveData<Boolean>
        get() = _insertDateInitializer

    private val _onGoBackToMain = MutableLiveData<Boolean>()
    val onGoBackToMain: LiveData<Boolean>
        get() = _onGoBackToMain

    private val readAll: LiveData<List<Note>>
    private val repository: NoteRepository

    private val _onMarkType = MutableLiveData<Boolean>()
    val onMarkType: LiveData<Boolean>
        get() = _onMarkType

    private val _noteOpacity = MutableLiveData<Int>()
    val noteOpacity: LiveData<Int>
        get() = _noteOpacity

    private var noteType: Int = -1
    private var newNoteType: Int = -1

    init {
        val noteDao = NoteDatabase.getInstance(application).noteDao
        repository = NoteRepository(noteDao)
        readAll = repository.readAllData
    }

    fun insertNoteInitializer() {
        _insertInitializer.value = true
    }

    fun insertNoteDateInitializer() {
        _insertDateInitializer.value = true
    }

    fun addNote(title: String, date: String) {
        val input = Note(0, title, date, type = noteType)
        insert(input)
        Log.e("this", "Added $input")
    }

    fun insert(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)
        }
    }

//    if you want to update noteDate
//    fun updateNote(id: Int, newTitle: String, newDate: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.updateNote(id, newTitle, newDate, newNoteType)
//        }
//    }

    fun updateNote(id: Int, newTitle: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(id, newTitle, newNoteType)
        }
    }

    fun onMarkType() {
        _onMarkType.value = true
    }

    fun onMarkTypeReset() {
        _onMarkType.value = false
    }

    fun onSetNoteType(type: Int) {
        _noteOpacity.value = type

        noteType = type
        newNoteType = type
    }

    fun onGoBack() {
        _onGoBackToMain.value = true
    }
}