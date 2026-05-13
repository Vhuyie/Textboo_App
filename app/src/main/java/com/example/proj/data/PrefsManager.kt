package com.example.proj.data

import android.content.Context
import com.example.proj.models.Textbook
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PrefsManager {

    private const val PREF_NAME = "textbook_prefs"
    private const val KEY_BOOKS = "books"

    fun saveBooks(context: Context, list: List<Textbook>) {

        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val gson = Gson()
        val json = gson.toJson(list)

        editor.putString(KEY_BOOKS, json)
        editor.apply()
    }

    fun getBooks(context: Context): MutableList<Textbook> {

        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val json = prefs.getString(KEY_BOOKS, null)

        if (json == null) return mutableListOf()

        val type = object : TypeToken<MutableList<Textbook>>() {}.type

        return Gson().fromJson(json, type)
    }
}
