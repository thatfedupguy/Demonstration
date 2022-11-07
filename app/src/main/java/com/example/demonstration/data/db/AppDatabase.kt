package com.example.demonstration.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.demonstration.models.User

@Database(entities = [User::class],
version = 1,
exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object{
        private var appDatabase: AppDatabase ?= null
        private val LOCK = Any()

        operator fun invoke(context: Context) = appDatabase ?: synchronized(LOCK){
            appDatabase ?: buildDatabase(context).also {
                appDatabase = it
            }
        }

        private fun buildDatabase(context: Context): AppDatabase{
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "AppDatabase.db"
            ).fallbackToDestructiveMigration().build()
        }
    }
}