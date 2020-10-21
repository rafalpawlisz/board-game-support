package io.github.rafalpawlisz.boardgamesupport

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cube -> true
            R.id.imago -> true
            R.id.wielki_zaklad -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}