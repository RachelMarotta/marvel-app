package com.rachelmarotta.marvelapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.rachelmarotta.marvelapp.data.ApiService
import com.rachelmarotta.marvelapp.data.MarvelService
import com.rachelmarotta.marvelapp.data.utils.generateMd5Hash
import com.rachelmarotta.marvelapp.model.Characters
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private val publicKey = BuildConfig.MARVEL_PUBLIC_KEY
    private val privateKey = BuildConfig.MARVEL_PRIVATE_KEY
    private val ts = System.currentTimeMillis().toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i(TAG, "onCreate: MainActivity is created")

        val limit = 20
        val offset = 0

        Log.i(TAG, "onCreate: Timestamp generated: $ts")
        Log.i(TAG, "onCreate: PrivateKey in BuildConfig: $privateKey")
        Log.i(TAG, "onCreate: PublicKey in BuildConfig: $publicKey")

        val hash = generateMd5Hash("$ts$privateKey$publicKey")

        Log.i(TAG, "onCreate: Hash generated: $hash")

        val apiService = ApiService.retrofit.create(MarvelService::class.java)
        val call = apiService.getCharacters(ts, publicKey, hash, limit, offset)

        call.enqueue(object : Callback<Characters> {
            override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
                Log.i(TAG, response.toString())
                if (response.isSuccessful) {
                    val marvelResponse = response.body()
                    Log.i(TAG, response.toString())
                } else {
                    // Handle the case where the response is not successful
                    Log.e(TAG, "Response was not successful")
                }
            }

            override fun onFailure(call: Call<Characters>, t: Throwable) {
                // Tratar falha
                Log.e("API Error", t.message.toString())
            }
        })
    }
}