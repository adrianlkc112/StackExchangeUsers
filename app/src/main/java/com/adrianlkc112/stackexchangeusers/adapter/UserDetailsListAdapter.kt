package com.adrianlkc112.stackexchangeusers.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adrianlkc112.stackexchangeusers.R
import com.adrianlkc112.stackexchangeusers.extensions.setFadeInAnimation
import com.adrianlkc112.stackexchangeusers.viewModel.UserDetailListViewModel

class UserDetailsListAdapter(private val context: Context,
                             private val dataSource: List<UserDetailListViewModel>) : RecyclerView.Adapter<UserDetailsListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_detail_record, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.setFadeInAnimation()

            val item = dataSource[position]

            holder.tvTitle.text = item.title
            holder.tvContent.text = item.content
        }

        override fun getItemCount(): Int {
            return dataSource.size
        }

        class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
            val tvTitle: TextView = view.findViewById(R.id.record_title)
            val tvContent: TextView = view.findViewById(R.id.record_content)
        }
    }