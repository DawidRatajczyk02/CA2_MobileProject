package com.ca2.eventfinder

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import java.text.SimpleDateFormat
import java.util.*
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ca2.eventfinder.api.RetrofitInstance
import com.ca2.eventfinder.model.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditEventActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var categoryEditText: EditText
    private lateinit var priceEditText: EditText
    private var eventId: Int = 0

    private lateinit var inputDateTime: EditText
    private val calendar = Calendar.getInstance()
    private val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)

        eventId = intent.getIntExtra("eventId", -1)
        val title = intent.getStringExtra("title")
        val date = intent.getStringExtra("date")
        val location = intent.getStringExtra("location")
        val description = intent.getStringExtra("description")
        val category = intent.getStringExtra("category")
        val price = intent.getDoubleExtra("price", 0.0)

        titleEditText = findViewById(R.id.titleEditText)
        dateEditText = findViewById(R.id.dateEditText)
        locationEditText = findViewById(R.id.locationEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        categoryEditText = findViewById(R.id.categoryEditText)
        priceEditText = findViewById(R.id.priceEditText)

        titleEditText.setText(title)
        dateEditText.setText(date)
        locationEditText.setText(location)
        descriptionEditText.setText(description)
        categoryEditText.setText(category)
        priceEditText.setText(price.toString())

        dateEditText.setOnClickListener {
            DatePickerDialog(this, { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                TimePickerDialog(this, { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)

                    dateEditText.setText(dateTimeFormat.format(calendar.time))
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        findViewById<Button>(R.id.saveButton).setOnClickListener {
            val updatedEvent = Event(
                eventId = eventId,
                title = titleEditText.text.toString(),
                dateTime = dateEditText.text.toString(),
                location = locationEditText.text.toString(),
                category = categoryEditText.text.toString(),
                description = descriptionEditText.text.toString(),
                price = priceEditText.text.toString().toDouble(),
                createdByUserId = 4
            )
            updateEvent(updatedEvent)
        }
    }

    private fun updateEvent(event: Event) {
        RetrofitInstance.api.updateEvent(event.eventId, event).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditEventActivity, getString(R.string.event_updated), Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this@EditEventActivity, getString(R.string.update_event_failed), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(this@EditEventActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
