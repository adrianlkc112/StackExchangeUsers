package com.adrianlkc112.stackexchangeusers.controller

import android.content.Context
import android.os.Bundle
import com.adrianlkc112.stackexchangeusers.R
import com.adrianlkc112.stackexchangeusers.model.User
import com.adrianlkc112.stackexchangeusers.viewModel.UserListViewModel

class MainController(savedInstanceState: Bundle?) {
    val userDataList = ArrayList<User>()
    val userViewModelList = ArrayList<UserListViewModel>()
    var userInputSearchText = ""
        private set

    init {
        if (savedInstanceState != null) {
            userDataList.clear()
            userDataList.addAll(savedInstanceState.getSerializable("main_userDataList") as ArrayList<User>)

            userViewModelList.clear()
            userViewModelList.addAll(savedInstanceState.getSerializable("main_userViewModelList") as ArrayList<UserListViewModel>)

            userInputSearchText = savedInstanceState.getString("main_userInputSearchText", "")
        }
    }

    fun setDataAndConvertViewModel(context: Context, inputList: List<User>, inputText: String)  {
        userInputSearchText = inputText

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

fun Bundle.saveMainController(mainController: MainController) {
    putSerializable("main_userDataList", mainController.userDataList)
    putSerializable("main_userViewModelList", mainController.userViewModelList)
    putString("main_userInputSearchText", mainController.userInputSearchText)
}
