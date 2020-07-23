package com.adrianlkc112.stackexchangeusers.util

import android.util.Log

fun StackTraceElement.generateTag() = className.substringAfterLast(".")
fun StackTraceElement.generateMessage(msg: Any = "") = "$methodName() Line $lineNumber: $msg"

@Suppress("UNCHECKED_CAST")
fun LogD(obj: Any = "") {
    Throwable().stackTrace[1].apply {
        Log.d(generateTag(), generateMessage(obj))
    }
}

@Suppress("UNCHECKED_CAST")
fun LogE(obj: Any = "") {
    Throwable().stackTrace[1].apply {
        Log.e(generateTag(), generateMessage(obj))
    }
}

@Suppress("UNCHECKED_CAST")
fun LogI(obj: Any = "") {
    Throwable().stackTrace[1].apply {
        Log.i(generateTag(), generateMessage(obj))
    }
}