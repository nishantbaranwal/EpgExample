package com.example.epgexample

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel(context: Context) : ViewModel() {
    private var channelInfoList:MutableLiveData<List<ChannelInfo>> = MutableLiveData()
    private var tvContractManager = TvContractManager(context)

    fun getChannelInfoList():LiveData<List<ChannelInfo>>{
        return channelInfoList
    }

    init{
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(tvContractManager.getAllChannelInfo()
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                channelInfoList.value = it
            }
        )
    }
}