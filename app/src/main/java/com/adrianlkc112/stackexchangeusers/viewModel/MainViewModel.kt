package com.adrianlkc112.stackexchangeusers.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.adrianlkc112.stackexchangeusers.R
import com.adrianlkc112.stackexchangeusers.model.User
import com.adrianlkc112.stackexchangeusers.viewData.UserListViewData

class MainViewModel(private val state: SavedStateHandle): ViewModel() {
    val userSearchHint: MutableLiveData<String> = state.getLiveData(STATE_USER_SEARCH_HINT)
    val userSearchInputText: MutableLiveData<String> = state.getLiveData(STATE_USER_SEARCH_INPUT_TEXT)

    private val userDataList: ArrayList<User> = state.get(STATE_USER_DATA_LIST)?: arrayListOf()
    val userListViewDataList: ArrayList<UserListViewData> = state.get(STATE_USER_LISTVIEW_DATA_LIST)?: arrayListOf()

    companion object {
        private const val STATE_USER_SEARCH_HINT = "userSearchHint"
        private const val STATE_USER_SEARCH_INPUT_TEXT = "userSearchInputText"
        private const val STATE_USER_DATA_LIST = "userDataList"
        private const val STATE_USER_LISTVIEW_DATA_LIST = "userListViewDataList"
    }

    fun saveState() {
        state[STATE_USER_SEARCH_HINT] = userSearchHint.value
        state[STATE_USER_SEARCH_INPUT_TEXT] = userSearchInputText.value

        state[STATE_USER_DATA_LIST] = userDataList
        state[STATE_USER_LISTVIEW_DATA_LIST] = userListViewDataList
    }

    fun setDataAndConvertViewModel(context: Context, inputList: List<User>)  {
        userListViewDataList.clear()
        userDataList.clear()
        userDataList.addAll(inputList.sortedBy { it.display_name })

        userListViewDataList.add(
            UserListViewData(
                UserListViewData.VIEW_TYPE_TITLE,
            -1,
            context.getString(R.string.main_user_name_title),
            context.getString(R.string.main_reputation_title))
        )

        for(user in userDataList) {
            userListViewDataList.add(
                UserListViewData(
                    UserListViewData.VIEW_TYPE_CONTENT,
                user.user_id,
                user.display_name,
                user.reputation.toString())
            )
        }
    }

    fun getSelectedUser(user_id: Int): User {
        return userDataList.filter { it.user_id == user_id }[0]
    }
}