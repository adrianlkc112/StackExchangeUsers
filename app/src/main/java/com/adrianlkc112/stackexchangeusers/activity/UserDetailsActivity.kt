package com.adrianlkc112.stackexchangeusers.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adrianlkc112.stackexchangeusers.R
import com.adrianlkc112.stackexchangeusers.adapter.UserDetailsListAdapter
import com.adrianlkc112.stackexchangeusers.controller.UserDetailsController
import com.adrianlkc112.stackexchangeusers.model.User
import com.adrianlkc112.stackexchangeusers.util.LogD
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_details.*
import kotlinx.android.synthetic.main.toolbar.*

class UserDetailsActivity : BaseActivity() {

    companion object {
        val ARG_USER = "user"
    }

    private lateinit var userDetailsController: UserDetailsController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        userDetailsController = UserDetailsController()

        if(intent.hasExtra(ARG_USER)) {
            val user = intent.getSerializableExtra(ARG_USER) as User
            userDetailsController.setDataAndConvertViewModel(this, user)
        } else {
            userDetailsController.setDataAndConvertViewModel(this, null)
        }

        initLayout()
    }

    private fun initLayout() {
        toolbar_title.text = getString(R.string.user_details_page_title)
        initAvatar()
        initUserDetailListView()
    }

    private fun initAvatar() {
        Picasso.get().load(userDetailsController.profileImage)
            .fit().centerInside()
            .into(avatar_imageview, object : Callback {
                override fun onSuccess() {
                }

                override fun onError(e: Exception?) {
                    avatar_imageview.setImageResource(R.drawable.empty_avatar_place_holder)
                }
            })
    }

    private fun initUserDetailListView() {
        user_listview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val userListAdapter = UserDetailsListAdapter(this, userDetailsController.userDetailsViewModelList)
        user_listview.adapter = userListAdapter
    }
}