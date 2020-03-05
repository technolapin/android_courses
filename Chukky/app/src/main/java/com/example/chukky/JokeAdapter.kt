package com.example.chukky

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JokeAdapter(private var jokes: List<Joke>):
    RecyclerView.Adapter<JokeAdapter.JokeViewHolder>()
{

    class JokeViewHolder(val text: TextView): RecyclerView.ViewHolder(text)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): JokeAdapter.JokeViewHolder {

        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.textview_layout, parent, false) as TextView


        return JokeViewHolder(textView)
    }

    override fun getItemCount() = jokes.size

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.text.text = jokes[position].value
    }


    fun addJoke(joke: Joke)
    {
        val m = jokes.toMutableList()
        m.add(0, joke)
        jokes = m.toList()
        Log.d("Adapter", "Jokes: ($jokes)")
        this.notifyItemInserted(0)
    }

}