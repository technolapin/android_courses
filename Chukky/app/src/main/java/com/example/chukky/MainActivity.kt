package com.example.chukky

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class JokeAdapter(private var jokes: List<String>):
    RecyclerView.Adapter<JokeAdapter.JokeViewHolder>()
{

    class JokeViewHolder(val textview: TextView): RecyclerView.ViewHolder(textview)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): JokeAdapter.JokeViewHolder {

        val textview = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.my_text_view, parent, false) as TextView


        return JokeViewHolder(textview)
    }

    override fun getItemCount() = jokes.size

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.textview.text = jokes[position]
    }
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layout_manager = LinearLayoutManager(this)
        val recycler = findViewById<RecyclerView>(R.id.recycler_id)
            .apply {
                setHasFixedSize(true)

                layoutManager = layout_manager

                //adapter = MyAdapter(myDataset)

            }



    }
}
