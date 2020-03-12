package com.example.chukky

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.widget.EdgeEffect
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

class MainActivity : AppCompatActivity() {
    private val disposables = CompositeDisposable();
    private val jokeService = JokeApiServiceFactory.make();
    private val adapter = JokeAdapter(
        listOf()
    ) {
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

        /*
        recycler.edgeEffectFactory = object : RecyclerView.EdgeEffectFactory() {
            override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect
            {
                return EdgeEffect(view.context).apply {
                    Log.d("lolilol", "SCROLLED PAST THE END");
                    adapter.onBottomReached(adapter);
                }
            }
        }
        */

        adapter.onBottomReached(adapter)
        Thread.sleep(1000)
        adapter.onBottomReached(adapter)
        Thread.sleep(1000)
        adapter.onBottomReached(adapter)
        Thread.sleep(1000)
        adapter.onBottomReached(adapter)
        Thread.sleep(1000)
        adapter.onBottomReached(adapter)
        Thread.sleep(1000)
        adapter.onBottomReached(adapter)
        Thread.sleep(1000)
        adapter.onBottomReached(adapter)
        Thread.sleep(1000)
        adapter.onBottomReached(adapter)

        recycler.viewTreeObserver
            .addOnScrollChangedListener(OnScrollChangedListener {

                Log.i("RECYCLER STATE", recycler.scrollState
                    .toString())

                Log.i("ADAPTER ITEM COUNT", adapter.itemCount.toString())
                val child = recycler.getChildAt(recycler.childCount-1)
                if (child != null)
                {
 //                   Log.i("SCROLL STATE", child.y.toString())
   //                 Log.i("RECYCLER NOT STATE", (recycler.height + recycler.scrollY).toString())
                    val delta = child.y - recycler.height
                    Log.i("DELTA", delta.toString())
                    if (delta > -20 && false)
                    {
     //                   Log.i("AEAZEZAEZAEZAEAZE", "EXTENDING")
                        adapter.onBottomReached(adapter)
    //                        Thread.sleep(1000)
                    }
                }
                else
                {
                }
                /*
                if (recycler.getChildAt(0).bottom
                    <= recycler.height + recycler.scrollY
                ) { //scroll view is at bottom
                } else { //scroll view is not at bottom
                    Log.i("SCROLL", "AT BOTTOM");
                    adapter.onBottomReached(adapter)
                }
                */

            })


    }


    override fun onDestroy() {
        this.disposables.clear()
        super.onDestroy()
    }


}
