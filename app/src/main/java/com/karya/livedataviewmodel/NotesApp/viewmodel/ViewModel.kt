package com.karya.livedataviewmodel.NotesApp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.karya.livedataviewmodel.NotesApp.database.NotesDatabase
import com.karya.livedataviewmodel.NotesApp.repository.Repository
import com.karya.livedataviewmodel.NotesApp.room.entity.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    val readAllNotes : LiveData<List<Notes>>

    init {
        val dao = NotesDatabase.getDatabase(application).dao()
        repository = Repository(dao)
        readAllNotes = dao.readAllNotes()
    }

    fun addData(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addData(notes)
        }
    }

    fun updateNote(notes: Notes){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateNote(notes)
        }
    }

    fun deleteNote(notes: Notes){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteNote(notes)
        }
    }

    fun deleteAllNotes(){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAllNotes()
        }
    }

    fun searchData(searchQuery:String): LiveData<List<Notes>>{
        return repository.searchData(searchQuery)
    }

}
