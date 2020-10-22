package io.github.rafalpawlisz.boardgamesupport

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val gameGeneratorHolder = GameGeneratorHolder()
    private var gameGenerator: GameGenerator = gameGeneratorHolder.getCubeGameGenerator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_layout.setOnClickListener {
            gameGenerator.generate().setAsResult()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        gameGeneratorHolder.run {
            gameGenerator = when (item.itemId) {
                R.id.cube -> getCubeGameGenerator()
                R.id.imago -> return true
                R.id.wielki_zaklad -> return true
                else -> return false
            }
        }
        return true
    }

    private fun String.setAsResult() {
        setOf(
            text_top_left,
            text_top_right,
            text_bottom_left,
            text_bottom_right
        ).forEach { it.text = this }
    }
}