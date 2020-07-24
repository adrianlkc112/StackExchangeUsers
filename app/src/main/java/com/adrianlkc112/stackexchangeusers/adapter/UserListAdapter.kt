package com.adrianlkc112.stackexchangeusers.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adrianlkc112.stackexchangeusers.R
import com.adrianlkc112.stackexchangeusers.callback.UserListCallback
import com.adrianlkc112.stackexchangeusers.extensions.setFadeInAnimation
import com.adrianlkc112.stackexchangeusers.viewModel.UserListViewModel

class UserListAdapter(private val context: Context,
                      private val dataSource: List<UserListViewModel>,
                      private val callback: UserListCallback) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return if(viewType == UserListViewModel.VIEW_TYPE_TITLE) {
                 ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_list_title, parent, false))
            } else {
                 ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_list_record, parent, false))
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.setFadeInAnimation()

            val item = dataSource[position]

            holder.tvReputation.text = item.reputation
            holder.tvUserName.text = item.display_name

            if(item.viewType == UserListViewModel.VIEW_TYPE_CONTENT) {
                holder.recordLayout.setOnClickListener {
                    callback.onUserListClick(item.user_id)
                }
            }
        }

        override fun getItemCount(): Int {
            return dataSource.size
        }

        override fun getItemViewType(position: Int): Int {
            return dataSource[position].viewType
        }

        class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
            val recordLayout: View = view.findViewById(R.id.record_layout)
            val tvReputation: TextView = view.findViewById(R.id.record_reputation)
            val tvUserName: TextView = view.findViewById(R.id.record_username)
        }
    }