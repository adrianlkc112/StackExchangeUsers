package com.adrianlkc112.stackexchangeusers.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adrianlkc112.stackexchangeusers.R
import com.adrianlkc112.stackexchangeusers.adapter.UserListAdapter
import com.adrianlkc112.stackexchangeusers.callback.UserListCallback
import com.adrianlkc112.stackexchangeusers.databinding.ActivityMainBinding
import com.adrianlkc112.stackexchangeusers.extensions.afterObserveOn
import com.adrianlkc112.stackexchangeusers.server.APIService
import com.adrianlkc112.stackexchangeusers.util.LogD
import com.adrianlkc112.stackexchangeusers.util.LogE
import com.adrianlkc112.stackexchangeusers.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        installTls12()              //handle devices that missing protocol TLSv1.2 and result in movie api SSLHandshakeException

        initLayout()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveState()
    }

    private fun initLayout() {
        initUserListView()
        initSearchEditText()

        search_button.setOnClickListener {
            doSearch()
        }
    }

    private fun initSearchEditText() {
        viewModel.userSearchHint.value = getString(R.string.main_search_hint)
        input_search_edittext.setOnFocusChangeListener { view, isFocus ->
            if(isFocus || !input_search_edittext.text.isNullOrEmpty()) {
                viewModel.userSearchHint.value = getString(R.string.main_search_hint) + " " +
                        getString(R.string.main_search_hint_min_requirement)
            } else {
                viewModel.userSearchHint.value = getString(R.string.main_search_hint)
            }
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
        val userListAdapter = UserListAdapter(this, viewModel.userListViewDataList, object: UserListCallback {
            override fun onUserListClick(user_id: Int) {
                val user = viewModel.getSelectedUser(user_id)
                LogD("Test User list clicked: ${user_id} , $user")

                val intent = Intent(this@MainActivity, UserDetailsActivity::class.java)
                intent.putExtra(UserDetailsActivity.ARG_USER, user)
                startActivity(intent)
            }
        })
        user_listview.adapter = userListAdapter
        user_listview.scheduleLayoutAnimation()
        displayNoData(viewModel.userListViewDataList.isEmpty())
    }

    private fun doSearch() {
        val userInputText = viewModel.userSearchInputText.value
        if(userInputText != null && userInputText.length >= 2) {
            getUserListFromServer(userInputText)
        } else {
            showMessageDialog(message = getString(R.string.err_msg_min_user_name))
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
                    viewModel.setDataAndConvertViewModel(this@MainActivity, response.items)
                    user_listview.adapter!!.notifyDataSetChanged()
                    user_listview.scheduleLayoutAnimation()
                    displayNoData(response.items.isEmpty())
                },
                { error ->
                    LogE(error.toString())
                    showMessageDialog(message = getString(R.string.err_msg_fail_users))
                }
            )
        }
    }

    private fun displayNoData(isNoData: Boolean) {
        if(isNoData) {
            user_listview.visibility = View.GONE
            user_no_data_textview.visibility = View.VISIBLE
        } else {
            user_listview.visibility = View.VISIBLE
            user_no_data_textview.visibility = View.GONE
        }
    }

    private fun installTls12() {    //https://ankushg.com/posts/tls-1.2-on-android/
        try {
            ProviderInstaller.installIfNeeded(this)
        } catch (e: GooglePlayServicesRepairableException) {
            // Prompt the user to install/update/enable Google Play services.
            GoogleApiAvailability.getInstance().showErrorNotification(this, e.connectionStatusCode)
        } catch (e: GooglePlayServicesNotAvailableException) {
            // Indicates a non-recoverable error: let the user know.
        }
    }
}