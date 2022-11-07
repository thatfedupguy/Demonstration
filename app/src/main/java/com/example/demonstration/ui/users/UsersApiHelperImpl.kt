package com.example.demonstration.ui.users

import com.example.demonstration.data.remote.UsersApi
import com.example.demonstration.data.remote.UsersApiHelper
import com.example.demonstration.models.User
import javax.inject.Inject
import retrofit2.Response

class UsersApiHelperImpl @Inject constructor(
    private val usersApi: UsersApi
): UsersApiHelper {
    override suspend fun getUsers(since: String, perPage: String): Response<List<User>> = usersApi.getUsers(since, perPage)
}