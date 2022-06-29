package com.karya.livedataviewmodel.NotesApp.repository

import androidx.lifecycle.LiveData
import com.karya.livedataviewmodel.NotesApp.room.entity.Notes
import com.karya.livedataviewmodel.NotesApp.room.dao.Dao

class Repository(private val dao: Dao) {

    suspend fun addData(notes: Notes){
        dao.addData(notes)
    }

    val readAllNotes : LiveData<List<Notes>> = dao.readAllNotes()
    suspend fun updateNote(notes: Notes){
        dao.updateNote(notes)
    }

    suspend fun deleteNote(notes: Notes){
        dao.deleteNote(notes)
    }

    suspend fun deleteAllNotes(){
        dao.deleteAllNotes()
    }

    fun searchData(searchQuery: String): LiveData<List<Notes>>{
        return dao.searchData(searchQuery)
    }

}