package com.ca2.eventfinder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import java.util.Locale
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ca2.eventfinder.api.RetrofitInstance
import com.ca2.eventfinder.model.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter

    private val addEventLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            fetchEvents()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val addEventButton = findViewById<Button>(R.id.addEventButton)
        addEventButton.setOnClickListener {
            val intent = Intent(this, AddEventActivity::class.java)
            addEventLauncher.launch(intent)
        }

        val languageButton = findViewById<Button>(R.id.languageButton)
        setLanguageButtonText(languageButton)
        languageButton.setOnClickListener {
            val currentLang = Locale.getDefault().language
            if (currentLang == "es") {
                setLocale("en")
            } else {
                setLocale("es")
            }
            setLanguageButtonText(languageButton)
        }

        fetchEvents()
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        recreate()
    }

    private fun setLanguageButtonText(languageButton: Button) {
        val currentLang = Locale.getDefault().language
        if (currentLang == "es") {
            languageButton.text = getString(R.string.lang)
        } else {
            languageButton.text = getString(R.string.lang)
        }
    }

    private fun fetchEvents() {
        RetrofitInstance.api.getEvents().enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        eventAdapter = EventAdapter(this@MainActivity, it)
                        recyclerView.adapter = eventAdapter
                    }
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: android.content.Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            fetchEvents()
        }
    }
}