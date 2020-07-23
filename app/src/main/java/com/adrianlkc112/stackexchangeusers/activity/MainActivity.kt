package com.adrianlkc112.stackexchangeusers.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adrianlkc112.stackexchangeusers.R
import com.adrianlkc112.stackexchangeusers.adapter.UserListAdapter
import com.adrianlkc112.stackexchangeusers.extensions.afterObserveOn
import com.adrianlkc112.stackexchangeusers.model.User
import com.adrianlkc112.stackexchangeusers.server.APIService
import com.adrianlkc112.stackexchangeusers.util.LogE
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private val userList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLayout()
    }

    private fun initLayout() {
        setupUserList()

        search_button.setOnClickListener {
            if(input_search_edittext.text.isNotEmpty()) {
                getUserListFromServer(input_search_edittext.text.toString())
            } else {
                showMessageDialog(message = getString(R.string.err_msg_empty_user_name))
            }
        }
    }

    private fun setupUserList() {
        user_listview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val userListAdapter = UserListAdapter(this, userList)
        user_listview.adapter = userListAdapter
    }

    private fun getUserListFromServer(name: String) {
        APIService.api.getUsers(inname = name).afterObserveOn {
            doOnSubscribe {
                showLoading()
            }.doOnTerminate {
                hideLoading()
            }.subscribe(
                { response ->
                    userList.clear()
                    userList.addAll(response.items)
                    user_listview.adapter!!.notifyDataSetChanged()
                },
                { error ->
                    LogE(error.toString())
                    showMessageDialog(message = getString(R.string.err_msg_fail_users))
                }
            )
        }
    }
}