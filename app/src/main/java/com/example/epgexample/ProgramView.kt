package com.example.epgexample

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.program_layout.view.*

class ProgramView(context: Context,attributeSet: AttributeSet?=null,defStyle:Int=-1) : LinearLayout(context, attributeSet, defStyle){
    init {
        LayoutInflater.from(context).inflate(R.layout.program_layout,this)
    }
    fun getProgramName() : TextView{
        return program_name
    }
}