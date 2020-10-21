package io.github.rafalpawlisz.boardgamesupport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_layout.setOnClickListener {
            random.nextInt(10).toString().apply {
                text_top_left.text = this
                text_top_right.text = this
                text_bottom_left.text = this
                text_bottom_right.text = this
            }
        }
    }
}