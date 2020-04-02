package com.example.chukky

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JokeList(var jokes: List<Joke>){
    fun get(position: Int): CharSequence? {
        return this.jokes[position].value
    }

    fun getSize(): Int {
        return this.jokes.size
    }

    fun addJoke(joke: Joke) {
        val m = jokes.toMutableList()
        m.add(0, joke)
        jokes = m.toList()
    }

}

class JokeAdapter(
    var jokes: JokeList,
    val onEndReached: (JokeAdapter) -> Unit?
) :
    RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    class JokeViewHolder(val text: TextView) : RecyclerView.ViewHolder(text)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JokeAdapter.JokeViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.textview_layout, parent, false) as TextView


        return JokeViewHolder(textView)
    }

    override fun getItemCount() = jokes.getSize()

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.text.text = jokes.get(position)
    }


    fun addJoke(joke: Joke) {
        this.jokes.addJoke(joke)
        Log.d("Adapter", "Jokes: ($jokes)")
        this.notifyItemInserted(0)
    }

}