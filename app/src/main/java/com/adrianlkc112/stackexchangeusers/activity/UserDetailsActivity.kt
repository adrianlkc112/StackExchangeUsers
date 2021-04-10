package com.adrianlkc112.stackexchangeusers.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adrianlkc112.stackexchangeusers.R
import com.adrianlkc112.stackexchangeusers.adapter.UserDetailsListAdapter
import com.adrianlkc112.stackexchangeusers.databinding.ActivityUserDetailsBinding
import com.adrianlkc112.stackexchangeusers.model.User
import com.adrianlkc112.stackexchangeusers.viewModel.UserDetailsViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_details.*
import kotlinx.android.synthetic.main.toolbar.*

class UserDetailsActivity : BaseActivity() {

    companion object {
        val ARG_USER = "user"
    }

    private lateinit var binding: ActivityUserDetailsBinding
    private val viewModel: UserDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_details)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        if(savedInstanceState == null) {
            if (intent.hasExtra(ARG_USER)) {
                val user = intent.getSerializableExtra(ARG_USER) as User
                viewModel.init(this, user)
            } else {
                viewModel.init(this, null)
            }
        }

        initLayout()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveState()
    }

    private fun initLayout() {
        toolbar_title.text = getString(R.string.user_details_page_title)
        initAvatar()
        initUserDetailListView()
    }

    private fun initAvatar() {
        if(viewModel.profileImage.isNotEmpty()) {
            Picasso.get().load(viewModel.profileImage)
                .fit().centerInside()
                .into(avatar_imageview, object : Callback {
                    override fun onSuccess() {
                    }

                    override fun onError(e: Exception?) {
                        avatar_imageview.setImageResource(R.drawable.empty_avatar_place_holder)
                    }
                })
        } else {
            avatar_imageview.setImageResource(R.drawable.empty_avatar_place_holder)
        }
    }

    private fun initUserDetailListView() {
        user_listview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val userListAdapter = UserDetailsListAdapter(this, viewModel.userDetailsListViewDataList)
        user_listview.adapter = userListAdapter
        user_listview.scheduleLayoutAnimation()
    }
}