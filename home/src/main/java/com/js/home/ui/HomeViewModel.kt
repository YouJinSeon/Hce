package com.js.home.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.js.domain.usecase.login.GetUserUseCase
import com.js.shard.model.User
import com.js.shard.result.successOr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
   private val userUseCase: GetUserUseCase
): ViewModel() {

    val userListLiveData = MutableLiveData<ArrayList<User>>()
    private val userList = ArrayList<User>()

    init {
        userListLiveData.value = userList
    }

    fun addUser(name: String) {
        viewModelScope.launch {
            val userInfo = async { userUseCase(
                GetUserUseCase.Param(
                    name = name
                )
            ) }
            userList.add(userInfo.await().successOr(User()))
            userListLiveData.value = userList
        }
    }

}