package com.adrianlkc112.stackexchangeusers.model

import java.io.Serializable

data class BadgeCount (
    val bronze: Int,
    val gold: Int,
    val silver: Int
): Serializable