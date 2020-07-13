package io.github.lightkun10.wordboxes

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

        // Reset mode if no data being saved
//        if (savedInstanceState == null) {

//        }
//        // Save data when screen orientation changed
//        else {
//            title = savedInstanceState.getString(STATE_TITLE).toString()
//            val stateList = savedInstanceState.getParcelableArrayList<Hero>(STATE_LIST)
//            val stateMode = savedInstanceState.getInt(STATE_MODE)
//
//            setActionBarTitle(title)
//
//            if(stateList != null) {
//                list.addAll(stateList)
//            }
//
//            // Don't forget to set mode to saved mode
//            setMode(stateMode)
//        }

        showLoading(true)
        showRecyclerGrid()

        // setActionBarTitle(title)
    }

/*============================== END OF MAIN FUNCTIONALITY ==============================*/

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putString(STATE_TITLE, title)
//        outState.putParcelableArrayList(STATE_LIST, list)
//        outState.putInt(STATE_MODE, mode)
//    }

    /*********************************************************************************************
     * EDIT THIS
     *********************************************************************************************/
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
            }
        })
    }

//    private fun showRecyclerGrid() {
//        /* Perlu menentukan nilai pada metode setLayoutManager() saja
//           untuk menentukan bagaimana recyclerview ditampilkan. */
//        rv_icons.layoutManager = GridLayoutManager(this, 2)
//        val gridHeroAdapter = GridHeroAdapter(list)
//        rv_icons.adapter = gridHeroAdapter
//
//        gridHeroAdapter.setOnItemClickCallback(object: GridHeroAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: Hero) {
//                showSelectedHero(data)
//            }
//        })
//    }
    /*********************************************************************************************
     * END OF EDIT SECTION
     *********************************************************************************************/

    /* REVIEW: This class(MenuInflater) is used to instantiate menu XML files into Menu objects */
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // convert menu_main.xml into a menu displayed in activity
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        setMode(item.itemId)
//        return super.onOptionsItemSelected(item)
//    }

    /* Handle events when each item being selected/pressed */
//    private fun setMode(selectedMode: Int) {
//        when(selectedMode) {
////            R.id.action_list -> {
////                title = "Mode List"
////                showRecyclerList()
////            }
//            R.id.action_grid -> {
//                title = "Mode Grid"
//                showRecyclerGrid()
//            }
//            R.id.action_cardview -> {
//                title = "Mode CardView"
//                showRecyclerCardView()
//            }
//        }
//
//        mode = selectedMode
//        setActionBarTitle(title)
//    }

    private fun showSelectedIcon(icon: Icon) {
        Toast.makeText(this, "Kamu memilih ${icon.iconTags[0]}", Toast.LENGTH_SHORT).show()
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