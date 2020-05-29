package com.example.epgexample

import android.content.Context
import android.media.tv.TvContract
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.tvprovider.media.tv.Channel
import androidx.tvprovider.media.tv.Program
import io.reactivex.Observable
import java.lang.Exception

class TvContractManager(private val context: Context) {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getAllChannelInfo(): Observable<List<ChannelInfo>> {
        val channelInfoList : MutableList<ChannelInfo> = ArrayList()
        try {
            context.contentResolver.query(TvContract.Channels.CONTENT_URI,null,
                null,null,null).use {
                while (it!!.moveToNext()){
                    val channel = Channel.fromCursor(it)
                    //read all programs related to channel and append in channel info list
                    channelInfoList.add(ChannelInfo(channel, getPrograms(getChannelUri(channel.id))))
                }
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
        return Observable.just(channelInfoList)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getPrograms(channelUri: Uri): List<Program> {
        val uri =TvContract.buildProgramsUriForChannel(channelUri)
        val programs : MutableList<Program> = ArrayList()
        try{
            context.contentResolver.query(uri,null,null,null,null).use {
                while(it!!.moveToNext()){
                    val program = Program.fromCursor(it)
                    programs.add(program)
                }
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
        return programs
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getChannelUri(channelId: Long): Uri {
        return TvContract.buildChannelUri(channelId)
    }
}
