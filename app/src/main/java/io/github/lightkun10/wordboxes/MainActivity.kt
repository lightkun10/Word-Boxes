package io.github.lightkun10.wordboxes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

/*============================== MAIN FUNCTIONALITY ============================*/

class MainActivity : AppCompatActivity() {

    private lateinit var iconViewModel: IconViewModel

    private var title = "Mode List"
    private var mode: Int = 0

    companion object {
        private const val STATE_TITLE = "state_string"
        private const val STATE_LIST = "state_list"
        private const val STATE_MODE = "state_mode"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // atur fixed size recyclerview yang sudah dibuat di activity_main
        rv_icons.setHasFixedSize(true)

        /* Kode diatas menjelaskan bahwa:
           bila fixed size bernilai true, maka recyclerview dapat melakukan optimasi
           ukuran lebar & tinggi secara otomatis. Nilai lebar dan tinggi
           recyclerview menjadi konstan. */

        showLoading(true)
        showRecyclerGrid()
    }


    private fun showRecyclerGrid() {
        /* Perlu menentukan nilai pada metode setLayoutManager() saja
           untuk menentukan bagaimana recyclerview ditampilkan. */
        rv_icons.layoutManager = GridLayoutManager(this, 2)
        val gridHeroAdapter = IconAdapter()
        rv_icons.adapter = gridHeroAdapter

        iconViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            IconViewModel::class.java)


        iconViewModel.getIcons().observe(this, Observer { icons ->
            if (icons != null) {
                gridHeroAdapter.setData(icons)
                Toast.makeText(this, "Success Parsing image", Toast.LENGTH_SHORT).show()
                showLoading(false)
            } else {
                Toast.makeText(this@MainActivity, "Error parsing", Toast.LENGTH_SHORT).show()
                showLoading(true)
            }
        })

        iconViewModel.setIcons()

        gridHeroAdapter.setOnItemClickCallback(object: IconAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Icon) {
                showSelectedIcon(data)

                /** Do the operation for changing data below... */
                val toWordboxPage =  Intent(this@MainActivity, WordboxActivity::class.java)
                toWordboxPage.putExtra(WordboxActivity.EXTRA_ICON_ID, data.iconId)
                toWordboxPage.putExtra(WordboxActivity.EXTRA_ICON_IMG, data.iconImg)
                // toWordboxPage.putExtra(WordboxActivity.EXTRA_ICON_TAGS, data.iconTags[Rand(data.iconTags.size - 1).returnRandomInt()])
                toWordboxPage.putExtra(WordboxActivity.EXTRA_ICON_TAGS, data.iconTags[0])
                startActivity(toWordboxPage)
            }
        })
    }

    private fun showSelectedIcon(icon: Icon) {
        Toast.makeText(this, "You are selecting: ${icon.iconTags[0]}", Toast.LENGTH_SHORT).show()
    }

    private fun setActionBarTitle(title: String?) {
        supportActionBar?.title = title
    }

    // Hide/show progess bar
    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}