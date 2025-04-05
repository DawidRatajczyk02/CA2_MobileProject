package com.ca2.eventfinder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ca2.eventfinder.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.activity.result.contract.ActivityResultContracts
import android.util.Log
import com.ca2.eventfinder.model.Event

class EventDetailActivity : AppCompatActivity() {

    private val editEventLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                fetchEventDetails()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val eventId = intent.getIntExtra("eventId", -1)
        val title = intent.getStringExtra("title")
        val date = intent.getStringExtra("date")
        val location = intent.getStringExtra("location")
        val description = intent.getStringExtra("description")
        val category = intent.getStringExtra("category")
        val price = intent.getDoubleExtra("price", 0.0)

        findViewById<TextView>(R.id.detailTitle).text = title
        findViewById<TextView>(R.id.detailDate).text = date
        findViewById<TextView>(R.id.detailLocation).text = location
        findViewById<TextView>(R.id.detailDescription).text = description
        findViewById<TextView>(R.id.detailCategory).text = category
        findViewById<TextView>(R.id.detailPrice).text = "€%.2f".format(price)

        findViewById<Button>(R.id.detailButton).setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

        findViewById<Button>(R.id.editButton).setOnClickListener {
            val intent = Intent(this, EditEventActivity::class.java).apply {
                putExtra("eventId", eventId)
                putExtra("title", title)
                putExtra("date", date)
                putExtra("location", location)
                putExtra("description", description)
                putExtra("category", category)
                putExtra("price", price)
            }
            editEventLauncher.launch(intent)
        }

        findViewById<Button>(R.id.deleteButton).setOnClickListener {
            if (eventId != null) {
                showDeleteConfirmationDialog(eventId)
            } else {
                Toast.makeText(this, "Invalid event ID", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showDeleteConfirmationDialog(eventId: Int) {
        AlertDialog.Builder(this)
            .setTitle("Delete Event")
            .setMessage("Are you sure you want to delete this event?")
            .setPositiveButton("Delete") { _, _ ->
                deleteEvent(eventId)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteEvent(eventId: Int) {
        RetrofitInstance.api.deleteEvent(eventId).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EventDetailActivity, "Event deleted", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this@EventDetailActivity, "Delete failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(this@EventDetailActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun fetchEventDetails() {
        val eventId = intent.getIntExtra("eventId", -1)

        if (eventId == -1) {
            Toast.makeText(this, "Invalid event ID", Toast.LENGTH_SHORT).show()
            return
        }

        RetrofitInstance.api.getEvents().enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful && response.body() != null) {
                    val event = response.body()!!.find { it.eventId == eventId }

                    if (event != null) {
                        findViewById<TextView>(R.id.detailTitle).text = event.title
                        findViewById<TextView>(R.id.detailDate).text = event.dateTime
                        findViewById<TextView>(R.id.detailLocation).text = event.location
                        findViewById<TextView>(R.id.detailDescription).text = event.description
                        findViewById<TextView>(R.id.detailCategory).text = event.category
                        findViewById<TextView>(R.id.detailPrice).text = "€%.2f".format(event.price)
                    } else {
                        Toast.makeText(this@EventDetailActivity, "Event not found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@EventDetailActivity, "Failed to fetch events", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                Toast.makeText(this@EventDetailActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

}