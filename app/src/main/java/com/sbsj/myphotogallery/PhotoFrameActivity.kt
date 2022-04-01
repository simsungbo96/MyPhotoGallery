package com.sbsj.myphotogallery

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import java.util.*
import kotlin.concurrent.timer

class PhotoFrameActivity : AppCompatActivity() {

    private val photoList = mutableListOf<Uri>()
    private var currentPosition = 0;
    private var timer : Timer? =null
    private val photoImageView: ImageView by lazy {
        findViewById<ImageView>(R.id.photoImageView)
    }


    private val backgroundPhotoImageView: ImageView by lazy {
        findViewById<ImageView>(R.id.backgroundPhotoImageView)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_frrame)
        getPhotoUriFromIntent()
    }

    override fun onStart() {
        super.onStart()
        startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
    override fun onStop() {
        super.onStop()
        timer?.cancel()

    }


    private fun getPhotoUriFromIntent() {
        val size = intent.getIntExtra("photoSize", 0)
        for (i in 0..size) {
            intent.getStringExtra("photo$i")?.let {
                photoList.add(Uri.parse(it))
            }
        }
    }

    private fun startTimer() {
        timer(period = 5 * 1000) {
            runOnUiThread {
                val current = currentPosition
                val next = if(photoList.size<=currentPosition+1) 0 else currentPosition + 1
                backgroundPhotoImageView.setImageURI(photoList[current])
                photoImageView.alpha = 0f
                photoImageView.setImageURI(photoList[next])
                photoImageView.animate()
                    .alpha(1.0f)
                    .setDuration(1000)
                    .start()
                currentPosition =next

            }
        }
    }
}