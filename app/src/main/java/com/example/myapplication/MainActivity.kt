package com.example.myapplication
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    val namae = listOf("Giscard", "Placé", "Morano", "Jean-Luc", "Manu", "Aurélion de Gicle de Glairovin")
        .sortedBy{
            it.length
        }

    fun log_push(line: String)
    {
        val logs = findViewById<TextView>(R.id.log_text_id)
        Log.d("events log", line)
        logs.text = line + "\n" +logs.text // not important
    }

    override fun onDestroy() {
        log_push("DESTROY")
        super.onDestroy()
    }

    override fun onResume() {
        log_push("RESUME")
        super.onResume()
    }

    override fun onPause() {
        log_push("PAUSE")
        super.onPause()
    }

    override fun onStop() {
        log_push("STOP")
        super.onStop()
    }

    override fun onStart() {
        log_push("START")
        super.onStart()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(null, namae.joinToString(" ") )




        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button_id)

        val onClickListener = button?.setOnClickListener {
            button.text = namae[Random.nextInt(0, 4)]
            log_push("BUTTON PUSHED")
        }

    }

}
