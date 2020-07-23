package com.adrianlkc112.stackexchangeusers.model

import com.adrianlkc112.stackexchangeusers.enum.UserType
import java.time.LocalDateTime
import java.util.*

data class User (
    val about_me: String?,
    val accept_rate: Int?,
    val account_id: Int,
    val age: Int?,
    val answer_count: Int,
    val badge_counts: BadgeCount,
    val creation_date: Long,
    val display_name: String,
    val down_vote_count: Int,
    val is_employee: Boolean,
    val last_access_date: Long,
    val last_modified_date: Long?,
    val link: String,
    val profile_image: String,
    val question_count: Int,
    val reputation: Int,
    val reputation_change_day: Int,
    val reputation_change_month: Int,
    val reputation_change_quarter: Int,
    val reputation_change_week: Int,
    val reputation_change_year: Int,
    val timed_penalty_date: Long?,
    val up_vote_count: Int,
    val user_id: Int,
    val user_type: UserType,
    val view_count: Int,
    val website_url: String?
)