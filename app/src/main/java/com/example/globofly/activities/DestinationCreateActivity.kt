package com.example.globofly.activities;

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.globofly.R
import com.example.globofly.models.Destination
import com.example.globofly.services.DestinationService
import com.example.globofly.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DestinationCreateActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_create)

		var toolbar = findViewById<Toolbar>(R.id.toolbar)
		var btn_add = findViewById<Button>(R.id.btn_add)
		var et_city = findViewById<EditText>(R.id.et_city)
		var et_description = findViewById<EditText>(R.id.et_description)
		var et_country = findViewById<EditText>(R.id.et_country)

		setSupportActionBar(toolbar)
		val context = this

		// Show the Up button in the action bar.
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		btn_add.setOnClickListener {
			val newDestination = Destination()
			newDestination.city = et_city.text.toString()
			newDestination.description = et_description.text.toString()
			newDestination.country = et_country.text.toString()

			// To be replaced by retrofit code
			/*SampleData.addDestination(newDestination)*/

            val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
			val requestCall = destinationService.addDestination(newDestination)

			requestCall.enqueue(object : Callback<Destination> {
				override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
				 if(response.isSuccessful){
				 	//Move back to DestinationListActivity
				 	finish()
					 //response of newly created obj
					 var newlyCreatedDestination = response.body()
					 Toast.makeText(context,"Successfully Added",Toast.LENGTH_SHORT).show()
				 }else{
				 	Toast.makeText(context,"Failed to add an item",Toast.LENGTH_SHORT).show()
				 }
				}

				override fun onFailure(call: Call<Destination>, t: Throwable) {
					Toast.makeText(context,"Failed to add an item",Toast.LENGTH_SHORT).show()
				}
			})



		}
	}
}

