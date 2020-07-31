package com.adrianlkc112.stackexchangeusers.util

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


object SpannableStringBuilderUtil {

    @Throws(JSONException::class)
    fun spannableString2JsonString(ss: SpannableStringBuilder): String {
        val json = JSONObject()
        json.put("text", ss.toString())
        val ja = JSONArray()
        val spans = ss.getSpans(
            0, ss.length,
            ForegroundColorSpan::class.java
        )
        for (i in spans.indices) {
            val col = spans[i].foregroundColor
            val start = ss.getSpanStart(spans[i])
            val end = ss.getSpanEnd(spans[i])
            val ij = JSONObject()
            ij.put("color", col)
            ij.put("start", start)
            ij.put("end", end)
            ja.put(ij)
        }
        json.put("spans", ja)
        return json.toString()
    }

    @Throws(JSONException::class)
    fun jsonString2SpannableString(strJson: String): SpannableStringBuilder {
        val json = JSONObject(strJson)
        val ss = SpannableStringBuilder(json.getString("text"))
        val ja = json.getJSONArray("spans")
        for (i in 0 until ja.length()) {
            val jo = ja.getJSONObject(i)
            val col = jo.getInt("color")
            val start = jo.getInt("start")
            val end = jo.getInt("end")
            ss.setSpan(ForegroundColorSpan(col), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return ss
    }
}