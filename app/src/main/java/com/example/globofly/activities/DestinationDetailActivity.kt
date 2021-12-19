package com.example.globofly.activities;

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.globofly.R
import com.example.globofly.helpers.SampleData
import com.example.globofly.models.Destination
import com.example.globofly.services.DestinationService
import com.example.globofly.services.ServiceBuilder
import com.google.android.material.appbar.CollapsingToolbarLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DestinationDetailActivity : AppCompatActivity() {
    lateinit var et_city: EditText
    lateinit var et_description: EditText
    lateinit var et_country: EditText
    lateinit var collapsing_toolbar: CollapsingToolbarLayout
    lateinit var detail_toolbar: Toolbar
    lateinit var btn_delete: Button
    lateinit var btn_update: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destiny_detail)

        detail_toolbar = findViewById<Toolbar>(R.id.detail_toolbar)
        setSupportActionBar(detail_toolbar)

        btn_delete = findViewById(R.id.btn_delete)
        btn_update = findViewById(R.id.btn_update)
        et_city = findViewById<EditText>(R.id.et_city)
        et_description = findViewById<EditText>(R.id.et_description)
        et_country = findViewById<EditText>(R.id.et_country)
        collapsing_toolbar = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

            val id = intent.getIntExtra(ARG_ITEM_ID, 0)

            loadDetails(id)

            initUpdateButton(id)

            initDeleteButton(id)
        }
    }

    private fun loadDetails(id: Int) {

        // To be replaced by retrofit code
        /*
         val destination = SampleData.getDestinationById(id)
           destination?.let {
             et_city.setText(destination.city)
             et_description.setText(destination.description)
             et_country.setText(destination.country)

             collapsing_toolbar.title = destination.city
         }*/

        val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
        val requestCall = destinationService.getDestination(id)

        requestCall.enqueue(object : retrofit2.Callback<Destination> {


            override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                if (response.isSuccessful) {
                    val destination = response.body()

                    destination?.let {
                        et_city.setText(destination.city)
                        et_description.setText(destination.description)
                        et_country.setText(destination.country)

                        collapsing_toolbar.title = destination.city
                    }
                } else {
                    //Handle Application Specific error
                    Toast.makeText(
                        this@DestinationDetailActivity,
                        "Failed to retrieve details", Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<Destination>, t: Throwable) {
                Toast.makeText(
                    this@DestinationDetailActivity,
                    "Failed to retrieve details", Toast.LENGTH_LONG
                ).show()
            }

        })

    }

    private fun initUpdateButton(id: Int) {

        btn_update.setOnClickListener {

            val city = et_city.text.toString()
            val description = et_description.text.toString()
            val country = et_country.text.toString()

            // To be replaced by retrofit code
            /*      val destination = Destination()
                  destination.id = id
                  destination.city = city
                  destination.description = description
                  destination.country = country

                  SampleData.updateDestination(destination);*/


            val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
            val requestCall = destinationService.updateDestination(id, city, description, country)

            requestCall.enqueue(object : Callback<Destination> {
                override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                    if (response.isSuccessful) {
                        finish() // Move back to DestinationListActivity
                        //response received
                        var updateDestination = response.body()
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            "Item Updated Successfully", Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            "Failed to Update Item", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Destination>, t: Throwable) {
                    Toast.makeText(
                        this@DestinationDetailActivity,
                        "Failed to Update Item", Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    private fun initDeleteButton(id: Int) {

        btn_delete.setOnClickListener {

            // To be replaced by retrofit code
           // SampleData.deleteDestination(id)

            val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
            val requestCall = destinationService.deleteDestination(id)

            requestCall.enqueue(object: Callback<Unit>{
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                   if(response.isSuccessful){
                       finish()
                       Toast.makeText(this@DestinationDetailActivity,"Successfully deleted",Toast.LENGTH_SHORT).show()
                   }else{
                       Toast.makeText(this@DestinationDetailActivity,"Failed to deleted",Toast.LENGTH_SHORT).show()

                   }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(this@DestinationDetailActivity,"Failed to deleted",Toast.LENGTH_SHORT).show()
                }
            })


        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, DestinationListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"
    }
}
