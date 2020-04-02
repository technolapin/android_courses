package com.example.chukky

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.widget.ProgressBar
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
import java.util.concurrent.TimeUnit


interface JokeApiService {
    @GET("jokes/random/")
    fun giveMeAJoke(): Single<Joke>;
}

object JokeApiServiceFactory {
    fun make(): JokeApiService {
        val builder = Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/")
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return builder.create<JokeApiService>()
    }

}

class MainActivity : AppCompatActivity()
{
    private val disposables = CompositeDisposable();
    private var loading = false
    private val jokeService = JokeApiServiceFactory.make();
    private val adapter = JokeAdapter(
        listOf()
    ) {
        this.loading = false
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar_id)

        val jokeDispo = jokeService
            .giveMeAJoke()
            .delay(200, TimeUnit.MILLISECONDS)
            .repeat(10)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                progressBar.visibility = View.VISIBLE
            }
            .doOnTerminate {
                val recycler = findViewById<RecyclerView>(R.id.recycler_id)
                recycler.adapter?.notifyDataSetChanged()

                this.loading = false
                progressBar.visibility = View.INVISIBLE
            }
            .subscribeBy(
                onError = { t: Throwable ->
                    Log.e(
                        "JokeError",
                        "Could not load Joke ($t)"
                    )
                },
                onNext = { j: Joke ->
                    Log.i(
                        "Joke",
                        j.toString()
                    ); it.addJoke(j)
                },
                onComplete = {
                    this.disposables.clear()
                }
            )
        this.disposables.add(jokeDispo)
        Unit

    }

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


        adapter.onEndReached(adapter)
        recycler.viewTreeObserver
            .addOnScrollChangedListener(OnScrollChangedListener {

                if (!recycler.canScrollVertically(-1) && !this.loading) {
                    Log.i("SCROLL", "top reached")
                    adapter.onEndReached(adapter)
                }
            })


    }


    override fun onDestroy() {
        this.disposables.clear()
        super.onDestroy()
    }


}
