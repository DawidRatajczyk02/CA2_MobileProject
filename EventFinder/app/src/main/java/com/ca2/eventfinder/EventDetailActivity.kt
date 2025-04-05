package com.ca2.eventfinder

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
import android.util.Log
import com.ca2.eventfinder.model.Event

class EventDetailActivity : AppCompatActivity() {
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

//        Log.d("EventDetailActivity", "Received eventId: $eventId")

        findViewById<TextView>(R.id.detailTitle).text = title
        findViewById<TextView>(R.id.detailDate).text = date
        findViewById<TextView>(R.id.detailLocation).text = location
        findViewById<TextView>(R.id.detailDescription).text = description
        findViewById<TextView>(R.id.detailCategory).text = category
        findViewById<TextView>(R.id.detailPrice).text = "€%.2f".format(price)

        findViewById<Button>(R.id.detailButton).setOnClickListener {
            finish()
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
}