package com.example.demonstration.data.remote

import com.example.demonstration.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {
    @GET("users")
    suspend fun getUsers(
        @Query("since") since: String,
        @Query("per_page") perPage: String
    ): Response<List<User>>
}