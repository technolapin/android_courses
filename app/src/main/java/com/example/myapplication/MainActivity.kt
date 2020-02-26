package com.example.myapplication
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.util.Log
import android.widget.Button
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    val namae = listOf("Giscard", "Placé", "Morano", "Jean-Luc", "Manu", "Aurélion de Gicle de Glairovin")
        .sortedBy{
            it.length
        }


    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d("tag nul", namae.joinToString(" ") )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button_id)


        val onClickListener = button?.setOnClickListener {
            button.text = namae[Random.nextInt(0, 4)]
        }

    }

    fun setOnClickListener(view: View) {


    }
}
