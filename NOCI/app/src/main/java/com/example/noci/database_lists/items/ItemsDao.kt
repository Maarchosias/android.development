package com.example.noci.database_lists.items

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemsDao {

    @Insert
    suspend fun addNote(note: Items)

//    @Query("INSERT * INTO note_table WHERE noteId = :key")
//    suspend fun insertType()

    @Query("SELECT * FROM item_table ORDER BY id ASC")
    fun readAll(): LiveData<List<Items>>

    @Query("SELECT * FROM item_table WHERE noteId = :id ORDER BY id ASC")
    fun readSpecificData(id: Int): LiveData<List<Items>>

    //@Query("SELECT COUNT(*) FROM note_table")
    //fun getTodayTasksCount(): LiveData<String>

    @Query("UPDATE item_table SET itemState = :newState WHERE id = :id")
    suspend fun editItem(id: Int, newState: Boolean)

    @Delete
    suspend fun deleteNote(noteId: Items)

    @Query( "DELETE FROM item_table WHERE noteId = :id")
    suspend fun deleteItemsFromSpecificList(id: Int)

//    @Update
//    fun update(note: NoteAttr)
//
//    @Query("SELECT * FROM notes_attributes WHERE noteId = :key")
//    fun get(key : Long): NoteAttr
//
//    @Query("DELETE FROM notes_attributes")
//    fun clear()
//
//    @Query("SELECT * FROM notes_attributes ORDER BY noteId DESC")
//    fun getAllNights(): LiveData<List<NoteAttr>>
//
//    @Query("SELECT * FROM notes_attributes ORDER BY noteId DESC LIMIT 1")
//    fun getTonight(): NoteAttr?
}