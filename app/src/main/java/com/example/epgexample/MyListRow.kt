package com.example.epgexample

import androidx.leanback.widget.ObjectAdapter

data class MyListRow (val headerItem: CustomHeaderItem, val adapter: ObjectAdapter,
                      val headerLayoutRes: Int)