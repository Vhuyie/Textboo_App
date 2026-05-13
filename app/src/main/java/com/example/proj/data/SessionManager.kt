package com.example.proj.data

import android.content.Context
import com.example.proj.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object SessionManager {

    private const val PREF_NAME = "APP_PREF"
    private const val USERS_KEY = "USERS"
    private const val CURRENT_USER = "CURRENT_USER"

    fun saveUsers(context: Context, users: List<User>) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = Gson().toJson(users)
        prefs.edit().putString(USERS_KEY, json).apply()
    }

    fun getUsers(context: Context): MutableList<User> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(USERS_KEY, null)

        return if (json != null) {
            val type = object : TypeToken<MutableList<User>>() {}.type
            Gson().fromJson(json, type)
        } else mutableListOf()
    }

    fun saveCurrentUser(context: Context, user: User) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(CURRENT_USER, Gson().toJson(user)).apply()
    }

    fun getCurrentUser(context: Context): User? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(CURRENT_USER, null)
        return if (json != null) Gson().fromJson(json, User::class.java) else null
    }

    fun logout(context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit().remove(CURRENT_USER).apply()
    }
}