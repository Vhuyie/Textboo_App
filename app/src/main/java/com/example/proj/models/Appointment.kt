package com.example.proj.models

data class Appointment(
    val id: String = java.util.UUID.randomUUID().toString(),

    val bookedBy: String,
    val bookedByName: String,

    val bookedWith: String,
    val bookedWithName: String,

    val date: String,
    val time: String,
    val notes: String,

    var status: String = "PENDING" // PENDING, ACCEPTED, REJECTED
)