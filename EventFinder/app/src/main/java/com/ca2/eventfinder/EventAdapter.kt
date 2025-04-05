package com.ca2.eventfinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.app.Activity
import com.ca2.eventfinder.model.Event

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class EventAdapter(
    private val activity: Activity,
    private val events: List<Event>
) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.eventTitle)
        val date: TextView = view.findViewById(R.id.eventDate)
        val location: TextView = view.findViewById(R.id.eventLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.title.text = event.title
        holder.date.text = event.dateTime
        holder.location.text = event.location

        holder.itemView.setOnClickListener {

            val intent = Intent(activity, EventDetailActivity::class.java).apply {
                putExtra("eventId", event.eventId)
                putExtra("title", event.title)
                putExtra("date", event.dateTime)
                putExtra("location", event.location)
                putExtra("description", event.description)
                putExtra("category", event.category)
                putExtra("price", event.price)

            }
            activity.startActivityForResult(intent, 1)
        }

        val input = event.dateTime
        val formattedDateTime = try {
            val parsed = LocalDateTime.parse(input)
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy - HH:mm")
            parsed.format(formatter)
        } catch (e: DateTimeParseException) {
            input // fallback to original
        }

        holder.date.text = formattedDateTime
    }

    override fun getItemCount() = events.size
}