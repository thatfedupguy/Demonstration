package com.example.demonstration.ui.users

import com.example.demonstration.data.db.UserDao
import com.example.demonstration.models.User
import com.example.demonstration.utils.ProgressStatus
import javax.inject.Inject
import retrofit2.Response

class UsersRepository @Inject constructor(
    private val usersApiHelperImpl: UsersApiHelperImpl,
    private val userDao: UserDao
) {
    suspend fun getUsers(since: String, perPage: String): Response<List<User>> = usersApiHelperImpl.getUsers(since, perPage)

    suspend fun getUsersFromDb(): List<User> = userDao.getUsers()

    suspend fun insertUsers(users: List<User>) = userDao.insert(users)
}