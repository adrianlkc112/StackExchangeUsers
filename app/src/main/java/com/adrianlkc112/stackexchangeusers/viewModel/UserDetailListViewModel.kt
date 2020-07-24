package com.adrianlkc112.stackexchangeusers.viewModel

import android.text.SpannableStringBuilder

data class UserDetailListViewModel (
    val title: SpannableStringBuilder,
    val content: SpannableStringBuilder
) {
    constructor(title: String, content: String) : this(SpannableStringBuilder(title), SpannableStringBuilder(content))
    constructor(title: String, content: SpannableStringBuilder) : this(SpannableStringBuilder(title), content)
}