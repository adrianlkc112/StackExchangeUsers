package com.adrianlkc112.stackexchangeusers.controller

import android.content.Context
import com.adrianlkc112.stackexchangeusers.R
import com.adrianlkc112.stackexchangeusers.model.BadgeCount
import com.adrianlkc112.stackexchangeusers.model.User
import com.adrianlkc112.stackexchangeusers.util.DateHelperUtil
import com.adrianlkc112.stackexchangeusers.viewModel.UserDetailListViewModel

class UserDetailsController {
    var profileImage: String = ""
        private set
    val userDetailsViewModelList = ArrayList<UserDetailListViewModel>()

    fun setDataAndConvertViewModel(context: Context, user: User?) {
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

    private fun convertBadgeToDisplay(context: Context, badgeCount: BadgeCount?): String {
        return if(badgeCount != null) {
            String.format(context.getString(R.string.badge_gold), badgeCount.gold.toString()) + "\n" +
                    String.format(context.getString(R.string.badge_silver), badgeCount.silver.toString()) + "\n" +
                    String.format(context.getString(R.string.badge_bronze), badgeCount.bronze.toString())
        } else {
            displayNA(context)
        }
    }

    private fun displayNA(context: Context): String {
        return context.getString(R.string.label_not_available)
    }
}