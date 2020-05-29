package com.example.epgexample

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.leanback.widget.HorizontalGridView
import com.bumptech.glide.Glide
import com.example.epgexample.databinding.CustomHeaderViewBinding
import kotlinx.android.synthetic.main.custom_lb_list_row.view.*


//custom_list_row_view is an holder or parent view for all views
// i.e. header_template, horizontal_grid_view and template inside one row
@SuppressLint("ViewConstructor")
class CustomListRowView(context: Context, layoutResource:Int, attributeSet: AttributeSet?)
    : LinearLayout(context,attributeSet){


    fun expandHeader(headerAdapter: CustomHeaderItem, layoutRes: Int) {
        header_container.removeAllViewsInLayout()
        val binding: CustomHeaderViewBinding = DataBindingUtil.inflate((context as Activity).layoutInflater,
            layoutRes,header_container,false)
        binding.headerData= headerAdapter
        header_container.addView(binding.root)
        header_container.isFocusableInTouchMode=true
        header_container.isFocusable = true
        header_container.setBackgroundColor(Color.parseColor("#000000"))
        header_container.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus) header_container.setBackgroundColor(Color.parseColor("#EEE8AA"))
            else header_container.setBackgroundColor(Color.parseColor("#000000"))
        }
    }
    private var mHorizontalGridView: HorizontalGridView?=null
    var id:Long?=null

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(layoutResource, this)
        mHorizontalGridView = findViewById(R.id.grid_view)
        mHorizontalGridView!!.setHasFixedSize(false)
        orientation = VERTICAL
//        descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS
//        getTitleTemplate().visibility = View.VISIBLE

    }

    companion object {
        @BindingAdapter("app:imageSource")
        @JvmStatic
        fun loadImage(imageView: ImageView, src: String?) {
            Log.d("fghjgnkhk",src)
            src?.let {
                Glide.with(imageView.context)
                    .load(src).timeout(40000)
                    .placeholder(R.drawable.placeholder_image).into(imageView)
            }
        }
    }
    fun getGridView():HorizontalGridView{
        return mHorizontalGridView!!
    }
    fun getHeaderContatiner():FrameLayout{
        return header_container
    }
}