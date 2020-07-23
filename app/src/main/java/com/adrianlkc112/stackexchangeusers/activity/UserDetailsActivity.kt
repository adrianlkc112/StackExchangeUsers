package com.adrianlkc112.stackexchangeusers.activity

import android.os.Bundle
import com.adrianlkc112.stackexchangeusers.R
import com.adrianlkc112.stackexchangeusers.model.User
import com.adrianlkc112.stackexchangeusers.util.LogD
import kotlinx.android.synthetic.main.toolbar.*

class UserDetailsActivity : BaseActivity() {

    companion object {
        val ARG_USER = "user"
    }

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        if(intent.hasExtra(ARG_USER)) {
            user = intent.getSerializableExtra(ARG_USER) as User
            LogD("Test User Details: $user")
            initLayout()
        }
    }

    private fun initLayout() {
        toolbar_title.text = getString(R.string.user_details_page_title)
        initUserListView()
    }

    private fun initUserListView() {
        /*
        user_listview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val userListAdapter = UserListAdapter(this, MainController.userViewModelList, this)
        user_listview.adapter = userListAdapter

         */
    }
}