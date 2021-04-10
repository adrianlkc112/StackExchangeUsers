package com.adrianlkc112.stackexchangeusers.viewData

import java.io.Serializable

data class UserListViewData (
    val viewType: Int,
    val user_id: Int,
    val display_name: String,
    val reputation: String
) : Serializable {
    companion object {
        val VIEW_TYPE_TITLE = 0
        val VIEW_TYPE_CONTENT = 1
    }
}