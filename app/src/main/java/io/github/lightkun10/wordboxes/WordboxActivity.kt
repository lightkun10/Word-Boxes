package io.github.lightkun10.wordboxes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.util.*

class WordboxActivity : AppCompatActivity() {

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wordbox)

        // Wire layout with controller
        val iconImage = findViewById<ImageView>(R.id.icon_image_view)
        var iconText = findViewById<TextView>(R.id.icon_title_view)

        // Parse intent data from MainActivity
        val transferedImg = intent.getStringExtra(EXTRA_ICON_IMG)
        val transferedText = intent.getStringExtra(EXTRA_ICON_TAGS)
        
        // Set icon detail image and its tag
        Glide.with(applicationContext).load(transferedImg).into(iconImage)
        iconText.text = transferedText?.capitalize(Locale.ROOT)
    }

    companion object {
        const val EXTRA_ICON_ID = "extra_icon_id"
        const val EXTRA_ICON_IMG = "extra_icon_img"
        const val EXTRA_ICON_TAGS = "extra_icon_tags"
    }
}