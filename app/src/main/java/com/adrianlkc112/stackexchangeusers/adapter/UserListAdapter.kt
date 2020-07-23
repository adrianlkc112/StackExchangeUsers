package com.adrianlkc112.stackexchangeusers.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adrianlkc112.stackexchangeusers.R
import com.adrianlkc112.stackexchangeusers.model.User

class UserListAdapter(private val context: Context, private val dataSource: List<User>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_list_record, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = dataSource[position]

            holder.tvReputation.text = item.reputation.toString()
            holder.tvUserName.text = item.display_name
        }

        override fun getItemCount(): Int {
            return dataSource.size
        }

        //override fun getItemViewType(position: Int): Int {
        //    return dataSource[position].viewType
        //}

        class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
            val tvReputation = view.findViewById<TextView>(R.id.record_reputation)
            val tvUserName = view.findViewById<TextView>(R.id.record_username)
        }
    }