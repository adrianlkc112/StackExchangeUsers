package com.adrianlkc112.stackexchangeusers.viewModel

import android.content.Context
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.adrianlkc112.stackexchangeusers.R
import com.adrianlkc112.stackexchangeusers.model.BadgeCount
import com.adrianlkc112.stackexchangeusers.model.User
import com.adrianlkc112.stackexchangeusers.util.DateHelperUtil
import com.adrianlkc112.stackexchangeusers.viewData.UserDetailListViewData

class UserDetailsViewModel(private val state: SavedStateHandle): ViewModel() {
    var profileImage: String = state.get(STATE_PROFILE_IMAGE)?: ""
        private set
    val userDetailsListViewDataList: ArrayList<UserDetailListViewData> = state.get(STATE_USER_DETAILS_LISTVIEW_DATA_LIST)?: arrayListOf()

    companion object {
        private const val STATE_PROFILE_IMAGE = "profileImage"
        private const val STATE_USER_DETAILS_LISTVIEW_DATA_LIST = "userDetailsListViewDataList"
    }

    fun saveState() {
        state[STATE_PROFILE_IMAGE] = profileImage
        state[STATE_USER_DETAILS_LISTVIEW_DATA_LIST] = userDetailsListViewDataList
    }

    fun init(context: Context, user: User?) {
        this.profileImage = user?.profile_image?: ""
        val na = displayNA(context)

        userDetailsListViewDataList.also {
            it.clear()
            it.add(
                UserDetailListViewData(context.getString(R.string.user_details_user_name),
                    user?.display_name?: na)
            )
            it.add(
                UserDetailListViewData(context.getString(R.string.user_details_reputation),
                    (user?.reputation?: na).toString())
            )
            it.add(
                UserDetailListViewData(context.getString(R.string.user_details_badges),
                    convertBadgeToDisplay(context, user?.badge_counts))
            )
            it.add(
                UserDetailListViewData(context.getString(R.string.user_details_location),
                    user?.location?: na)
            )
            it.add(
                UserDetailListViewData(context.getString(R.string.user_details_age),
                    (user?.age?: na).toString())
            )
            it.add(
                UserDetailListViewData(context.getString(R.string.user_details_create_date),
                    if(user != null) DateHelperUtil.convertEpochToDisplay(user.creation_date) else na)
            )
        }
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