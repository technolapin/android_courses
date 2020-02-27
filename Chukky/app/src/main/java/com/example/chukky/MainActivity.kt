package com.example.chukky

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


object JokeList
{
        val list = listOf("joke A", "joke B", "joke C")
}

class JokeAdapter(private var jokes: List<String>):
    RecyclerView.Adapter<JokeAdapter.JokeViewHolder>()
{

    class JokeViewHolder(val text: TextView): RecyclerView.ViewHolder(text)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): JokeAdapter.JokeViewHolder {

        val textView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.textview_layout, parent, false) as TextView


        return JokeViewHolder(textView)
    }

    override fun getItemCount() = jokes.size

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.text.text = jokes[position]
    }

    fun setJokes(new_jokes: List<String>)
    {
        jokes = new_jokes
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
