package intercept.situation1

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.viewproject.MyUtils
import com.example.viewproject.R
import com.example.viewproject.databinding.ActivityDemo1Binding
import ui.HorizontalScrollViewEx

class DemoActivity : AppCompatActivity() {
    private val TAG = "DemoActivity"
    private  lateinit var mListContainer: HorizontalScrollViewEx
    private lateinit var binding: ActivityDemo1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemo1Binding.inflate(layoutInflater)
        setContentView(binding.root)
//        initView()
        val valueAnimator = ValueAnimator.ofInt(0,100)
        valueAnimator.apply {
            duration = 100
            addUpdateListener {
                binding.test.translationX = it.animatedValue as Float
            }
            start()
        }


    }

    @SuppressLint("SetTextI18n")
    private fun initView(){
        val inflater = layoutInflater
        mListContainer = binding.container
        val screenWidth = MyUtils.getScreenMetrics(this).widthPixels
        val screenHeight = MyUtils.getScreenMetrics(this).heightPixels
        for (i in 0 until 3){
            val layout = inflater.inflate(R.layout.content_layout, mListContainer, false)
            layout.layoutParams.width = screenWidth
            val textView = layout.findViewById<TextView>(R.id.title)
            textView.text = "page ${i+1}"
            layout.layoutParams.height = screenHeight
            layout.setBackgroundColor(Color.rgb(255/(i+1), 255/(i+1),0))
            createList(layout as ViewGroup)
            mListContainer.addView(layout)
        }


    }

    private fun createList(layout: ViewGroup){
        val listView = layout.findViewById<ListView>(R.id.list)
        val date = mutableListOf<String>()
        for(i in 0 until 50){
            date.add("name $i")
        }
        val arrayAdapter = ArrayAdapter(this, R.layout.content_list_item,R.id.name,date)
        listView.adapter = arrayAdapter
    }
}