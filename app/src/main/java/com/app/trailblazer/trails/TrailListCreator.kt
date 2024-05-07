package com.app.trailblazer.trails

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TrailListCreator {
    fun getTrails(context: Context): List<Trail> {
        val filename = "trails.json"
        val jsonFileString = context.assets?.open(filename)?.bufferedReader().use { it?.readText() }

        val gson = Gson()
        val listType = object : TypeToken<List<Trail>>() {}.type

        return gson.fromJson(jsonFileString, listType)
    }
}