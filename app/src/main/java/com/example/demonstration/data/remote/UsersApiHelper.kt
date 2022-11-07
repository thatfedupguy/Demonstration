package com.example.demonstration.data.remote

import com.example.demonstration.models.User
import retrofit2.Response

interface UsersApiHelper {
    suspend fun getUsers(since: String, perPage: String): Response<List<User>>
}