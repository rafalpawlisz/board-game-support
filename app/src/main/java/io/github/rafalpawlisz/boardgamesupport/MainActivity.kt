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
            getRandomNumber(10).toString().setAsResult()
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

    private fun getRandomNumber(bound: Int) = random.nextInt(bound)

    private fun String.setAsResult() {
        setOf(
            text_top_left,
            text_top_right,
            text_bottom_left,
            text_bottom_right
        ).forEach { it.text = this }
    }
}