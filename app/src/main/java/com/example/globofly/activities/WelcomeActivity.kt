package com.example.globofly.activities;

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.globofly.R
import com.example.globofly.services.MessageService
import com.example.globofly.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WelcomeActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_welcome)

		val message = findViewById<TextView>(R.id.message)

		val messageService = ServiceBuilder.buildService(MessageService::class.java)
		/*pass complete url incase of calling data from another server*/
		val requestCall = messageService.getMessages("http://192.168.1.2:81/messages")
		requestCall.enqueue(object: Callback<String>{
			override fun onResponse(call: Call<String>, response: Response<String>) {
				if(response.isSuccessful){
					val msg = response.body()
					msg?.let{
						message.text = msg
					}
				}else{
					//Handle Application Specific error

					Toast.makeText(this@WelcomeActivity,
					"Failed to retrieve items",Toast.LENGTH_LONG).show()
				}
			}

			override fun onFailure(call: Call<String>, t: Throwable) {
				Toast.makeText(this@WelcomeActivity,
					"Failed to retrieve items",Toast.LENGTH_LONG).show()
			}
		})

		// To be replaced by retrofit code
		//message.text = "Black Friday! Get 50% cash back on saving your first spot."
	}

	fun getStarted(view: View) {
		val intent = Intent(this, DestinationListActivity::class.java)
		startActivity(intent)
		finish()
	}
}
