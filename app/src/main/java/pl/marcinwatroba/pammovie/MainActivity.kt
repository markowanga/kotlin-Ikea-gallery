package pl.marcinwatroba.pammovie

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    var time = 1

    companion object {
        const val PAUSE_TIME = "TIME"
        const val REQUEST_CODE = 10001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())

        Timber.d("oko")

        videoView.setVideoURI(Uri.parse("""android.resource://$packageName/${R.raw.movie2}"""))

        playPauseView.setOnClickListener({
            if (!playPauseView.isPlay)
                videoView.pause()
            else videoView.start()

            playPauseView.toggle()
        })

        videoView.setOnPreparedListener({ mp -> mp.isLooping = true })

        videoView.setOnTouchListener { _, _ ->
            if (!playPauseView.isPlay)
                playPauseView.callOnClick()

            openDetailsActivity()
            false
        }
    }

    override fun onResume() {
        super.onResume()
        videoView.seekTo(1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            finish()
        }
    }

    private fun openDetailsActivity() {
        val time = videoView.currentPosition
        val intent = Intent(applicationContext, PreviewActivity::class.java)

        intent.putExtra(PAUSE_TIME, time)
        startActivityForResult(intent, REQUEST_CODE)
    }
}
