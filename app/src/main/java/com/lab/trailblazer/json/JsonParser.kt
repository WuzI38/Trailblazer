package com.lab.trailblazer.json

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JsonParser {
    private fun trailObjectListFromJson(context: Context): List<Trail> {
        val filename = "trails.json"
        val jsonFileString = context.assets?.open(filename)?.bufferedReader().use { it?.readText() }

        val gson = Gson()
        val listType = object : TypeToken<List<Trail>>() {}.type

        return gson.fromJson(jsonFileString, listType)
    }
    fun trailListFromJson(context: Context): List<String> {
        val trails: List<Trail> = trailObjectListFromJson(context)
        return trails.map { it.trailName }
    }
    fun getTrailByName(context: Context, name: String): Trail? {
        val trails: List<Trail> = trailObjectListFromJson(context)
        return trails.find { it.trailName == name }
    }
}