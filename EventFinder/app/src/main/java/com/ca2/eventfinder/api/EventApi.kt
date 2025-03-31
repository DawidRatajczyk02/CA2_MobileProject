package com.ca2.eventfinder.api

import com.ca2.eventfinder.model.Event
import retrofit2.Call
import retrofit2.http.*

interface EventApi {
    @GET("api/Events")
    fun getEvents(): Call<List<Event>>

    @POST("api/Events")
    fun addEvent(@Body event: Event): Call<Event>

    @PUT("api/Events/{id}")
    fun updateEvent(@Path("id") id: Int, @Body event: Event): Call<Unit>

    @DELETE("api/Events/{id}")
    fun deleteEvent(@Path("id") id: Int): Call<Unit>
}