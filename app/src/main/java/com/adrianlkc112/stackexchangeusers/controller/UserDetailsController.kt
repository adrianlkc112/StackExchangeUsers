package com.adrianlkc112.stackexchangeusers.controller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.adrianlkc112.stackexchangeusers.R
import com.adrianlkc112.stackexchangeusers.activity.UserDetailsActivity
import com.adrianlkc112.stackexchangeusers.model.BadgeCount
import com.adrianlkc112.stackexchangeusers.model.User
import com.adrianlkc112.stackexchangeusers.util.DateHelperUtil
import com.adrianlkc112.stackexchangeusers.viewModel.UserDetailListViewModel

class UserDetailsController(context: Context, savedInstanceState: Bundle?, intent: Intent) {
    var profileImage: String = ""
        private set
    val userDetailsViewModelList = ArrayList<UserDetailListViewModel>()

    init {
        if (savedInstanceState != null) {
            profileImage = savedInstanceState.getString("userDetails_profileImage", "")

            userDetailsViewModelList.clear()
            userDetailsViewModelList.addAll(savedInstanceState.getSerializable("userDetails_userDetailsViewModelList") as ArrayList<UserDetailListViewModel>)
        } else {
            if (intent.hasExtra(UserDetailsActivity.ARG_USER)) {
                val user = intent.getSerializableExtra(UserDetailsActivity.ARG_USER) as User
                setDataAndConvertViewModel(context, user)
            } else {
                setDataAndConvertViewModel(context, null)
            }
        }
    }

    private fun setDataAndConvertViewModel(context: Context, user: User?) {
        this.profileImage = user?.profile_image?: ""
        val na = displayNA(context)

        userDetailsViewModelList.clear()
        userDetailsViewModelList.add(UserDetailListViewModel(context.getString(R.string.user_details_user_name),
                        user?.display_name?: na))
        userDetailsViewModelList.add(UserDetailListViewModel(context.getString(R.string.user_details_reputation),
                                (user?.reputation?: na).toString()))
        userDetailsViewModelList.add(UserDetailListViewModel(context.getString(R.string.user_details_badges),
                                convertBadgeToDisplay(context, user?.badge_counts)))
        userDetailsViewModelList.add(UserDetailListViewModel(context.getString(R.string.user_details_location),
                        user?.location?: na))
        userDetailsViewModelList.add(UserDetailListViewModel(context.getString(R.string.user_details_age),
                                (user?.age?: na).toString()))
        userDetailsViewModelList.add(UserDetailListViewModel(context.getString(R.string.user_details_create_date),
                                if(user != null) DateHelperUtil.convertEpochToDisplay(user.creation_date) else na))
    }

    private fun convertBadgeToDisplay(context: Context, badgeCount: BadgeCount?): SpannableStringBuilder {
        val builder = SpannableStringBuilder()

        if(badgeCount != null) {
            val goldStr = SpannableString(String.format(context.getString(R.string.badge_gold), badgeCount.gold.toString()))
            goldStr.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.badgeGold)), 0, goldStr.length, 0)
            builder.append(goldStr)
            builder.append("\n\n")

            val silverStr = SpannableString(String.format(context.getString(R.string.badge_silver), badgeCount.silver.toString()))
            silverStr.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.badgeSilver)), 0, silverStr.length, 0)
            builder.append(silverStr)
            builder.append("\n\n")

            val bronzeStr = SpannableString(String.format(context.getString(R.string.badge_bronze), badgeCount.bronze.toString()))
            bronzeStr.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.badgeBronze)), 0, bronzeStr.length, 0)
            builder.append(bronzeStr)
        } else {
            builder.append(displayNA(context))
        }

        return builder
    }

    private fun displayNA(context: Context): String {
        return context.getString(R.string.label_not_available)
    }
}

fun Bundle.saveUserDetailsController(userDetailsController: UserDetailsController) {
    putString("userDetails_profileImage", userDetailsController.profileImage)
    putSerializable("userDetails_userDetailsViewModelList", userDetailsController.userDetailsViewModelList)
}