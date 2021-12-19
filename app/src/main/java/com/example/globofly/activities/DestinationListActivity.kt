package com.example.globofly.activities;
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView
import com.example.globofly.R
import com.example.globofly.helpers.DestinationAdapter
import com.example.globofly.helpers.SampleData
import com.example.globofly.models.Destination
import com.example.globofly.services.DestinationService
import com.example.globofly.services.ServiceBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Callable


class DestinationListActivity : AppCompatActivity() {

	lateinit var toolbar:Toolbar
	lateinit var fab:FloatingActionButton


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_list)
		toolbar = findViewById(R.id.toolbar_destiny_list)
		fab = findViewById(R.id.fab)

		setSupportActionBar(toolbar)
		toolbar.title = title

		fab.setOnClickListener {
			val intent = Intent(this@DestinationListActivity, DestinationCreateActivity::class.java)
			startActivity(intent)
		}
	}

	override fun onResume() {
		super.onResume()

		loadDestinations()
	}

	private fun loadDestinations() {
		var destiny_recycler_view:RecyclerView = findViewById(R.id.destiny_recycler_view)

		//filter we can give multiple query parameters here
		val filter = HashMap<String,String>()
		/*filter["country"] = "India"
		filter["count"] = "1"*/

        // To be replaced by retrofit code
		//destiny_recycler_view.adapter = DestinationAdapter(SampleData.DESTINATIONS)
		val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
		val requestCall = destinationService.getDestinationList(filter)
		requestCall.enqueue(object: Callback<List<Destination>> {
			override fun onResponse(
				call: Call<List<Destination>>,
				response: Response<List<Destination>>
			) {
				if(response.isSuccessful){
					val destinationList:List<Destination> = response.body()!!
					destiny_recycler_view.adapter = DestinationAdapter(destinationList)
				}//Handle Application Specific error
				else if(response.code() == 401){
					Toast.makeText(this@DestinationListActivity,
					"your session has expired. Please login again",Toast.LENGTH_LONG).show()
				}//Handle Application Specific error
				else{
					//Application level failiure
					//your status code is in range of 300's , 400's, and 500's
				}
			}

			//Network/IO exceptions,Establishing connection with servers,Error in creating Request/Responses
			override fun onFailure(call: Call<List<Destination>>, t: Throwable) {
			Toast.makeText(this@DestinationListActivity,"Failed to retrieve items",Toast.LENGTH_LONG).show()
			}

		})
    }
}
