package com.adrianlkc112.stackexchangeusers.controller

import android.content.Context
import com.adrianlkc112.stackexchangeusers.R
import com.adrianlkc112.stackexchangeusers.model.User
import com.adrianlkc112.stackexchangeusers.viewModel.UserListViewModel

class MainController {
    private val userDataList = ArrayList<User>()
    val userViewModelList = ArrayList<UserListViewModel>()

    fun setDataAndConvertViewModel(context: Context, inputList: List<User>)  {
        userViewModelList.clear()
        userDataList.clear()
        userDataList.addAll(inputList.sortedBy { it.display_name })

        userViewModelList.add(UserListViewModel(UserListViewModel.VIEW_TYPE_TITLE,
                            -1,
                                    context.getString(R.string.main_user_name_title),
                                    context.getString(R.string.main_reputation_title)))

        for(user in userDataList) {
            userViewModelList.add(UserListViewModel(UserListViewModel.VIEW_TYPE_CONTENT,
                                    user.user_id,
                                    user.display_name,
                                    user.reputation.toString()))
        }
    }

    fun getSelectedUser(user_id: Int): User {
        return userDataList.filter { it.user_id == user_id }[0]
    }
}