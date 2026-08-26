package com.example.hexflow
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<TextView>(R.id.score)?.text = "Hex Flow MVP active (rank 19)"
    }
}
