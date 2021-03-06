package com.example.noci.database_lists.items

import androidx.lifecycle.LiveData
import com.example.noci.lists.CURRENT_LIST
import com.example.noci.lists.ITEM_DELETER_CHECKER
import com.orhanobut.hawk.Hawk

class ItemsRepository(private val itemsNoteDao: ItemsDao) {

    private val theAlmightyID: String? = Hawk.get<String>(CURRENT_LIST)

    //val readAllData: LiveData<List<Items>> = itemsNoteDao.readAll()

    val readSpecificData: LiveData<List<Items>>? = theAlmightyID?.toInt()?.let {
        itemsNoteDao.readSpecificData(
            it
        )
    }

    suspend fun addNote(note: Items) {
        itemsNoteDao.addNote(note)
    }

    suspend fun editNote(id: Int, newState: Boolean) {
        itemsNoteDao.editItem(id, newState)
    }

    suspend fun deleteNote(note: Items) {
        itemsNoteDao.deleteNote(note)
    }

    suspend fun selectAll(listId: Int, stateToBeSet: Boolean) {
        itemsNoteDao.selectAll(listId, stateToBeSet)
    }

    suspend fun copyData(listId: Int) : List<String>? {
        return itemsNoteDao.copyData(listId)
    }

    suspend fun onDeleteSelected(itemId: Int) {
        itemsNoteDao.onDeleteSelected(itemId)
    }

    suspend fun deleteItemsFromSpecificList() {
        val theItemsId = Hawk.get<String>(ITEM_DELETER_CHECKER)

        itemsNoteDao.deleteItemsFromSpecificList(theItemsId.toInt())
    }

}