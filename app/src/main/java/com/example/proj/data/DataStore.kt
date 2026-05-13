package com.example.proj.data

import com.example.proj.models.Appointment
import com.example.proj.models.Textbook
import com.example.proj.models.User

object DataStore {

    val textbooks = mutableListOf<Textbook>()

    val users = mutableListOf<User>()
    var currentUser: User? = null
    var hasNotification: Boolean = false

    //val appointments = mutableListOf<Any>()

    val appointments = mutableListOf<Appointment>()




}