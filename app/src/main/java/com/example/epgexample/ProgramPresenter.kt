package com.example.epgexample

import android.graphics.Color
import android.view.ViewGroup
import androidx.leanback.widget.Presenter

class ProgramPresenter(): Presenter() {

    var programView : ProgramView ?= null
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        programView = ProgramView(parent!!.context, null)
        programView!!.isFocusable = true
        programView!!.isFocusableInTouchMode = true
        return ViewHolder(programView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        if(item is ProgramListRow){
            val programView = viewHolder!!.view as ProgramView
            programView.getProgramName().text = item.titleName
            programView.setBackgroundColor(Color.BLACK)
            programView.setOnFocusChangeListener { v, hasFocus ->
                if(hasFocus) {
                    programView.getProgramName().setTextColor(Color.BLUE)
                    programView.setBackgroundColor(Color.parseColor("#EEE8AA"))
                }
                else {
                    programView.getProgramName().setTextColor(Color.WHITE)
                    programView.setBackgroundColor(Color.BLACK)
                }
            }
        }
     }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
    }
}