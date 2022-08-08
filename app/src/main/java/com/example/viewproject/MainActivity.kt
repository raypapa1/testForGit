package com.example.viewproject


import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.Scroller
import androidx.annotation.RequiresApi
import com.example.viewproject.databinding.ActivityMainBinding
import com.nineoldandroids.view.ViewHelper
import intercept.situation1.DemoActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var count = 0

    private val mHandler = object : Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            when(msg.what){
                1 ->{
                    Log.d("handleMessage", "handleMessage: $count")
                   count += 1
                    if(count < 30){
                        val fraction = count / 33f
                        val scrollX = (fraction * -100 ).toInt()
                        Log.d("handleMessage", "scrollX: $scrollX")

                        binding.myView.scrollTo(scrollX, 0)
                        sendEmptyMessageDelayed(1,33)
                    }
                }
            }
        }

    }

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val valueAnimator = ValueAnimator.ofInt(0,100)
        valueAnimator.apply {
            duration = 100
            addUpdateListener {
                binding.test.translationX = it.animatedValue as Float
            }
            start()
        }


    }


}



class MyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : androidx.appcompat.widget.AppCompatTextView(context,attrs,defStyleAttr) {
    private val TAG = "MyView"
    private var mLastX: Float = 0f
    private var mLastY: Float = 0f
    private val mScroller = Scroller(context)
    private var num = 1


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event?.rawX
        val y = event?.rawY
        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                Log.d(TAG, "onTouchEvent: ACTION_DOWN")
                smoothScrollTo(-20 * num, 0)
                num += 1
            }

            MotionEvent.ACTION_UP ->{
                Log.d(TAG, "onTouchEvent: ACTION_UP")

            }

            MotionEvent.ACTION_MOVE ->{
                val deltaX = x?.minus(mLastX)
                val deltaY = y?.minus(mLastY)
                Log.d(TAG, "onTouchEvent: x is $x y is $y move x $deltaX, move y $mLastY")
                val translationX = ViewHelper.getTranslationX(this) + deltaX!!
                val translationY = ViewHelper.getTranslationY(this) + deltaY!!
                ViewHelper.setTranslationX(this, translationX)
                ViewHelper.setTranslationY(this, translationY)

            }
            else ->{

            }

        }
        if (y != null) {
            mLastY = y
        }
        if (x != null) {
            mLastX = x
        }
        return true
    }

    private fun smoothScrollTo(desX: Int, desY:Int){
        val scrollX = scrollX
        val deltaX = desX - scrollX
        mScroller.startScroll(scrollX,0,deltaX,0,4000)
        invalidate()
    }

    override fun computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.currX, mScroller.currY)
            postInvalidate()
        }
    }

}
