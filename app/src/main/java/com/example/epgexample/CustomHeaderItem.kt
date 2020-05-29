package com.example.epgexample

import android.net.Uri
import androidx.leanback.widget.HeaderItem


//custom_header_item is a model class for custom_header
data class CustomHeaderItem(
    var id1: Long? = 0,
    var icon: Uri? = Uri.parse(""),
    var headerTitle: String? = ""
):HeaderItem(id1!!,headerTitle)