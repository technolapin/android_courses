package com.example.chukky

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


object JokeList
{
        val list = listOf("joke A", "joke B", "joke C")
            .map{
                    Joke(listOf(""), "", "", "", "", "", it)
            }
}


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val manager = LinearLayoutManager(this)
        val recycler = findViewById<RecyclerView>(R.id.recycler_id)
            .apply {
                setHasFixedSize(true)

                layoutManager = manager

                adapter = JokeAdapter(JokeList.list)

            }



    }
}
