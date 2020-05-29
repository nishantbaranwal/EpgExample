package com.example.epgexample

import androidx.tvprovider.media.tv.Channel
import androidx.tvprovider.media.tv.Program

data class ChannelInfo(val channel: Channel, val programs:List<Program>)
