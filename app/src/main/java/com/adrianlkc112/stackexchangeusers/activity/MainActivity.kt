package com.adrianlkc112.stackexchangeusers.activity

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adrianlkc112.stackexchangeusers.R
import com.adrianlkc112.stackexchangeusers.adapter.UserListAdapter
import com.adrianlkc112.stackexchangeusers.callback.UserListCallback
import com.adrianlkc112.stackexchangeusers.controller.MainController
import com.adrianlkc112.stackexchangeusers.extensions.afterObserveOn
import com.adrianlkc112.stackexchangeusers.server.APIService
import com.adrianlkc112.stackexchangeusers.util.LogD
import com.adrianlkc112.stackexchangeusers.util.LogE
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), UserListCallback {

    private lateinit var mainController: MainController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainController = MainController()

        initLayout()
    }

    private fun initLayout() {
        initUserListView()

        search_button.setOnClickListener {
            doSearch()
        }

        input_search_edittext.setOnEditorActionListener{ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch()
            }
            false
        }
    }

    private fun initUserListView() {
        user_listview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val userListAdapter = UserListAdapter(this, mainController.userViewModelList, this)
        user_listview.adapter = userListAdapter
    }

    private fun doSearch() {
        if(input_search_edittext.text.isNotEmpty()) {
            getUserListFromServer(input_search_edittext.text.toString())
        } else {
            showMessageDialog(message = getString(R.string.err_msg_empty_user_name))
        }
    }

    private fun getUserListFromServer(name: String) {
        APIService.api.getUsers(inname = name).afterObserveOn {
            doOnSubscribe {
                showLoading()
            }.doOnTerminate {
                hideLoading()
            }.subscribe(
                { response ->
                    mainController.setUserDataListAndConvertViewModel(this@MainActivity, response.items)
                    user_listview.adapter!!.notifyDataSetChanged()
                },
                { error ->
                    LogE(error.toString())
                    showMessageDialog(message = getString(R.string.err_msg_fail_users))
                }
            )
        }
    }

    override fun onUserListClick(user_id: Int) {
        LogD("Test User list clicked: ${user_id} , ${mainController.getSelectedUser(user_id)}")
    }
}