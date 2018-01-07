package pl.marcinwatroba.pammovie

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_preview.*
import timber.log.Timber

class PreviewActivity : AppCompatActivity(), GestureDetector.OnGestureListener {

    private var position: Int = 0
    private var moveCount = 0f

    companion object {
        private const val STEP = 12 // to set the speed of moving

        val plateMap = mapOf(
                8 to Pair(301, 743),
                9 to Pair(355, 743),
                10 to Pair(414, 743),
                11 to Pair(474, 743),
                12 to Pair(534, 743),
                13 to Pair(590, 743),
                14 to Pair(647, 743),
                15 to Pair(691, 743),
                16 to Pair(747, 743),
                17 to Pair(801, 743),
                18 to Pair(857, 743),
                19 to Pair(901, 743),
                20 to Pair(952, 743),
                21 to Pair(1005, 743),
                22 to Pair(1052, 743),
                23 to Pair(1102, 743),
                24 to Pair(1153, 743),
                25 to Pair(1200, 743),
                26 to Pair(1256, 743),
                27 to Pair(1307, 743),
                28 to Pair(1351, 743),
                29 to Pair(1407, 743),
                30 to Pair(1461, 743),
                31 to Pair(1502, 756))

        val glassMap = mapOf(
                56 to Pair(540, 1000),
                57 to Pair(515, 874),
                58 to Pair(490, 777),
                59 to Pair(465, 689),
                60 to Pair(430, 626),
                61 to Pair(399, 592),
                62 to Pair(367, 580),
                63 to Pair(336, 564),
                64 to Pair(304, 560),
                65 to Pair(276, 570),
                66 to Pair(245, 579),
                67 to Pair(219, 595))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        val time = intent.getIntExtra(MainActivity.PAUSE_TIME, 0)
        Timber.d("""time: $position""")

        position = time / (1000 / 24)
        Timber.d("""position${String.format("%03d", position)}""")
        setImage()

        val detector = GestureDetectorCompat(this, this)

        imageView.setOnTouchListener { _, motionEvent -> detector.onTouchEvent(motionEvent) }

        plateButton.setOnClickListener {
            ProductDetailsDialogFragment.newInstance(ProductDetailsDialogFragment.PLATE)
                    .show(supportFragmentManager, "")
        }

        glassButton.setOnClickListener {
            ProductDetailsDialogFragment.newInstance(ProductDetailsDialogFragment.GLASS)
                    .show(supportFragmentManager, "")
        }

        fab.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        setButtons()
    }

    private fun setImage() {
        imageView.setImageResource(getPhotoResId())
    }

    private fun getPhotoResId(): Int =
            resources.getIdentifier("pl.marcinwatroba.pammovie:drawable/photo" +
                    String.format("%04d", position), null, null)

    private fun setButtons() {
        if (plateMap.containsKey(position)) {
            plateButton.visibility = View.VISIBLE
            plateButton.x = plateMap[position]!!.first.toFloat() - 185
            plateButton.y = plateMap[position]!!.second.toFloat() - 165
        } else plateButton.visibility = View.GONE

        if (glassMap.containsKey(position)) {
            glassButton.visibility = View.VISIBLE
            glassButton.x = glassMap[position]!!.first.toFloat() - 100
            glassButton.y = glassMap[position]!!.second.toFloat() - 90
        } else glassButton.visibility = View.GONE
    }

    override fun onSingleTapUp(p0: MotionEvent?) = false

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        moveCount += p2
        val k = (moveCount / STEP).toInt()
        moveCount %= STEP
        position -= k

        if (position < 0)
            position += 144
        else if (position >= 144)
            position -= 144

        setImage()
        setButtons()

        return true
    }

    override fun onShowPress(p0: MotionEvent?) {}
    override fun onLongPress(p0: MotionEvent?) {}
    override fun onDown(p0: MotionEvent?) = true
    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float) = true
}
