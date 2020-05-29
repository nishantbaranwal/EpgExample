package com.example.epgexample

import android.view.ViewGroup
import androidx.leanback.widget.FocusHighlight
import androidx.leanback.widget.FocusHighlightHelper
import androidx.leanback.widget.ItemBridgeAdapter
import androidx.leanback.widget.Presenter

class MyListPresenter : Presenter(){

    private var customListRowView : CustomListRowView?=null
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        customListRowView = CustomListRowView(
            context = parent!!.context, layoutResource = R.layout.custom_lb_list_row,
            attributeSet = null
        )
        customListRowView!!.getGridView().post {
            customListRowView!!.getGridView().getChildAt(0).nextFocusLeftId =
                customListRowView!!.getHeaderContatiner().id
        }
    return ViewHolder(customListRowView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        if(item is MyListRow){
            val customListRowView = viewHolder!!.view as CustomListRowView
            customListRowView.expandHeader(item.headerItem, item.headerLayoutRes)
            val itemBridgeAdapter= ItemBridgeAdapter()
            itemBridgeAdapter.setAdapter(item.adapter)
            customListRowView.getGridView().adapter = itemBridgeAdapter
            customListRowView.getGridView().post {
                customListRowView.getGridView().getChildAt(0).nextFocusLeftId =
                    customListRowView.getHeaderContatiner().id
            }
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
    }
}