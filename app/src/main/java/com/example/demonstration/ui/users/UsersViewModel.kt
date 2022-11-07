package com.example.demonstration.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demonstration.models.User
import com.example.demonstration.utils.ProgressStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersRepository: UsersRepository
): ViewModel() {

    private val _usersResponse: MutableLiveData<ProgressStatus<List<User>>> = MutableLiveData()
    var usersResponse: LiveData<ProgressStatus<List<User>>> = _usersResponse

    fun getUsers(since: String, perPage: String) = viewModelScope.launch {
        try {
            _usersResponse.postValue(ProgressStatus.Loading())
            val result = usersRepository.getUsers(since, perPage)
            if (result.isSuccessful) {
                usersRepository.insertUsers(result.body()!!)
                _usersResponse.postValue(
                    ProgressStatus.DataSuccess(
                        result.body()
                    )
                )
            }else {
                _usersResponse.postValue(ProgressStatus.DataError(result.errorBody().toString()))
            }
        } catch (e: Exception){
            _usersResponse.postValue(ProgressStatus.DataError("Oops Something Went Wrong"))
        }
    }

    fun getUsersFromDb() = viewModelScope.launch {
        try{
            val users = usersRepository.getUsersFromDb()
            if(users.isNotEmpty()){
                _usersResponse.postValue(ProgressStatus.DataSuccess(users))
            } else {
                _usersResponse.postValue(ProgressStatus.DataError("No Users Found"))
            }
        } catch (e: Exception){
            _usersResponse.postValue(ProgressStatus.DataError("Oops Something Went Wrong"))
        }
    }

}