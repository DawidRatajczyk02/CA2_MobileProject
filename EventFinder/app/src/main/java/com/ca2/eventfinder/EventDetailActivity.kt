package com.ca2.eventfinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.ca2.eventfinder.model.Event

class EventDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val title = intent.getStringExtra("title")
        val date = intent.getStringExtra("date")
        val location = intent.getStringExtra("location")
        val description = intent.getStringExtra("description")

        findViewById<TextView>(R.id.detailTitle).text = title
        findViewById<TextView>(R.id.detailDate).text = date
        findViewById<TextView>(R.id.detailLocation).text = location
        findViewById<TextView>(R.id.detailDescription).text = description
    }
}