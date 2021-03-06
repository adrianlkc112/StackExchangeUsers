package com.adrianlkc112.stackexchangeusers.viewData

import android.text.SpannableStringBuilder
import com.adrianlkc112.stackexchangeusers.util.SpannableStringBuilderUtil
import java.io.Serializable

class UserDetailListViewData: Serializable {
    val titleJsonString: String
    val contentJsonString: String

    constructor(titleString: String, contentString: String) {
        titleJsonString = SpannableStringBuilderUtil.spannableString2JsonString(SpannableStringBuilder(titleString))
        contentJsonString = SpannableStringBuilderUtil.spannableString2JsonString(SpannableStringBuilder(contentString))
    }

    constructor(titleString: String, contentSSB: SpannableStringBuilder) {
        titleJsonString = SpannableStringBuilderUtil.spannableString2JsonString(SpannableStringBuilder(titleString))
        contentJsonString = SpannableStringBuilderUtil.spannableString2JsonString(contentSSB)
    }
}