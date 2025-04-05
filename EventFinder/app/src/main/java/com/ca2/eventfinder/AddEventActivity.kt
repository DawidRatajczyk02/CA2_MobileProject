package com.ca2.eventfinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import com.ca2.eventfinder.model.Event
import com.ca2.eventfinder.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        val inputTitle = findViewById<EditText>(R.id.inputTitle)
        val inputDescription = findViewById<EditText>(R.id.inputDescription)
        val inputLocation = findViewById<EditText>(R.id.inputLocation)
        val inputCategory = findViewById<EditText>(R.id.inputCategory)
        val inputPrice = findViewById<EditText>(R.id.inputPrice)
        val inputDateTime = findViewById<EditText>(R.id.inputDateTime)

        val calendar = Calendar.getInstance()
        val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())

        inputDateTime.setOnClickListener {
            DatePickerDialog(this, { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                TimePickerDialog(this, { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)

                    inputDateTime.setText(dateTimeFormat.format(calendar.time))
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        findViewById<Button>(R.id.backButton).setOnClickListener {
            finish()
        }

        val submitButton = findViewById<Button>(R.id.submitButton)
        submitButton.setOnClickListener {
            val title = inputTitle.text.toString().trim()
            val description = inputDescription.text.toString().trim()
            val location = inputLocation.text.toString().trim()
            val category = inputCategory.text.toString().trim()
            val price = inputPrice.text.toString().toDoubleOrNull() ?: 0.0
            val dateTime = inputDateTime.text.toString().trim()

            if (title.isEmpty() || dateTime.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newEvent = Event(
                eventId = 0,
                title = title,
                description = description,
                location = location,
                category = category,
                price = price,
                dateTime = dateTime,
                createdByUserId = 4
            )

            RetrofitInstance.api.addEvent(newEvent).enqueue(object : Callback<Event> {
                override fun onResponse(call: Call<Event>, response: Response<Event>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AddEventActivity, "Event added!", Toast.LENGTH_SHORT).show()
                        setResult(RESULT_OK)
                        finish()
                    } else {
                        Toast.makeText(this@AddEventActivity, "Failed to add event", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Event>, t: Throwable) {
                    Toast.makeText(this@AddEventActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }

    }
}


