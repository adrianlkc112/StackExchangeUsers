package com.adrianlkc112.stackexchangeusers.viewModel

data class UserListViewModel (
    val viewType: Int,
    val user_id: Int,
    val display_name: String,
    val reputation: String
) {
    companion object {
        val VIEW_TYPE_TITLE = 0
        val VIEW_TYPE_CONTENT = 1
    }
}