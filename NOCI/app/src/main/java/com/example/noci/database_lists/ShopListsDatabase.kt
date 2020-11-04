package com.example.noci.database_lists


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// create the database
@Database(entities = [ShopLists::class], version = 8, exportSchema = false)
abstract class ShopListsDatabase : RoomDatabase()
{
    // we declare the database accessibility class
    abstract val noteDao : ShopListDao

    companion object {

        // THE value OF INSTANCE IS ALWAYS UP TO DATE AND Volatile MAKES SURE THAT THERE ARE NO DIFFERENT threads PERFORMING THE SAME operation ( updating the database twice )
        @Volatile
        private var INSTANCE : ShopListsDatabase? = null

        // return the database
        fun getInstance(context : Context) : ShopListsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                // if null, create it
                if(instance == null) {
                    // build the database
                    instance = Room.databaseBuilder(context.applicationContext, ShopListsDatabase::class.java, "list_database").fallbackToDestructiveMigration().build()
                    // assign instance to the newly created database
                    INSTANCE = instance
                }

                // return the database
                return instance
            }
        }
    }
}