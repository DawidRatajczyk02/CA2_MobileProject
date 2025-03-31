package com.ca2.eventfinder.model

data class Event(
    val eventId: Int = 0,
    val title: String,
    val dateTime: String,
    val location: String,
    val category: String,
    val description: String,
    val price: Double,
    val createdByUserId: Int
)