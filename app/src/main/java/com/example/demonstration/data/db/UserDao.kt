package com.example.demonstration.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.demonstration.models.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: List<User>?)

    @Query("SELECT * FROM USER")
    suspend fun getUsers(): List<User>
}