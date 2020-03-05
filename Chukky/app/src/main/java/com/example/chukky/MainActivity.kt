package com.example.chukky

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.create
import retrofit2.http.GET
import java.util.*
import java.util.logging.Logger



interface JokeApiService
{
    @GET("jokes/random/")
    fun giveMeAJoke(): Single<Joke>;
}

object JokeApiServiceFactory
{
    fun make(): JokeApiService
    {
        val builder = Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/")
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return builder.create<JokeApiService>()
    }

}

class MainActivity : AppCompatActivity() {

    private val comp = CompositeDisposable();
    private val adapter = JokeAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapt = this.adapter

        val manager = LinearLayoutManager(this)
        val recycler = findViewById<RecyclerView>(R.id.recycler_id)
            .apply {
                setHasFixedSize(true)

                layoutManager = manager

                adapter = adapt

            }

        val jokeService = JokeApiServiceFactory.make()


        val button = findViewById<Button>(R.id.button_id)

        val onClickListener = button?.setOnClickListener {
            val jokeDispo = jokeService
                .giveMeAJoke()
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                    onError={ t: Throwable -> Log.e("JokeError", "Could not load Joke ($t)")},
                    onSuccess={ j: Joke ->  Log.i("Joke", j.toString()); adapter.addJoke(j); j }
                )
            this.comp.add(jokeDispo)

            Log.i("Button", "Click")
        }







    }

    override fun onDestroy() {
        this.comp.clear()
        super.onDestroy()
    }


}
