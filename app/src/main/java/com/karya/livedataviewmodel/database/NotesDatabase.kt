package com.karya.livedataviewmodel.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.karya.livedataviewmodel.Dao
import com.karya.livedataviewmodel.modelClass.Notes


@Database(entities = [Notes::class], version = 1)
abstract class NotesDatabase:RoomDatabase() {

    abstract fun dao(): Dao
    companion object{

        @Volatile
        private var INSTANCE: NotesDatabase? = null
        fun getDatabase(context: Context): NotesDatabase {
            if(INSTANCE != null){
               return INSTANCE!!
            }

            synchronized(this){

                INSTANCE = Room.databaseBuilder(
                    context.applicationContext, NotesDatabase::class.java,"notes"
                ).allowMainThreadQueries().build()
            }
            return INSTANCE!!
        }
    }
}
