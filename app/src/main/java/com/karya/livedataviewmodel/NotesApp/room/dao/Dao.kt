package com.karya.livedataviewmodel.NotesApp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.karya.livedataviewmodel.NotesApp.room.entity.Notes


@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addData(notes: Notes)

    @Query("SELECT * FROM notes ORDER BY id ASC ")
    fun readAllNotes() : LiveData<List<Notes>>

    @Update
    suspend fun updateNote(notes: Notes)

    @Delete
    suspend fun deleteNote(notes: Notes)

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM notes WHERE noteTitle LIKE :searchQuery")
    fun searchData(searchQuery: String) : LiveData<List<Notes>>


}