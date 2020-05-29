package com.example.epgexample

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.KEYCODE_DPAD_LEFT
import androidx.annotation.RequiresApi
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ItemBridgeAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityViewModel: MainActivityViewModel

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //uncomment for pushing the data and uncomment vice versa
//        ContentTask().execute()
//        val tvContractCompat =  TvContractManager(this)
//        tvContractCompat.getAllChannelInfo().observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io()).subscribe {
//            createEpg(it)
//        }
        //comment for pushing the data and uncomment vice versa
        mainActivityViewModel = ViewModelProviders.of(this,MyViewModelFactory(applicationContext))[MainActivityViewModel(this)::class.java]
        mainActivityViewModel.getChannelInfoList().observe(this, Observer {
            createEpg(it)
        })
    }

    @SuppressLint("RestrictedApi")
    private fun createEpg(channelList: List<ChannelInfo>) {
        val myListPresenter = MyListPresenter()
        val rowsAdapter = ArrayObjectAdapter(myListPresenter)
        for(channelInfo in channelList){
            Log.d("InfoOfChannel",channelInfo.channel.displayName + channelInfo.channel.appLinkIconUri)

            val cardPresenter = ProgramPresenter()
            val programAdapter = ArrayObjectAdapter(cardPresenter)

            for(program in channelInfo.programs) {
                val programListRow = ProgramListRow(program.title)
                programAdapter.add(programListRow)
            }

            val listRow = MyListRow(CustomHeaderItem(
                10,
                Uri.parse("https://ya-webdesign.com/images250_/netflix-app-icon-png-6.png")
                ,"Netflix"),programAdapter,
                R.layout.custom_header_view)

            rowsAdapter.add(listRow)
        }
        vertical_grid_view.setNumColumns(1)
        vertical_grid_view.adapter = ItemBridgeAdapter(rowsAdapter)
        vertical_grid_view.setOnChildSelectedListener { parent, view, position, id ->
            if(view is CustomListRowView){
                if(view.getGridView().getChildAt(0)!=null){
                    (view.getGridView().getChildAt(0)).setOnKeyListener { v, keyCode, event ->
                        if(event.action == ACTION_DOWN){
                            if(keyCode == KEYCODE_DPAD_LEFT)
                                view.getHeaderContatiner().requestFocus()
                        }
                        return@setOnKeyListener false
                    }
                }
            }
        }
    }

    
    //uncomment for pushing the data and uncomment vice versa
//    private fun createChannelPrograms(uri: Uri) {
//
//        val channelId= ContentUris.parseId(uri)
//
//        val programBuilder1 = PreviewProgram.Builder()
//
//        for (i in 1..10){
//            programBuilder1.setChannelId(channelId)
//
//                .setTitle("CustomProgram Program $i")
//                .setThumbnailUri(Uri.parse("https://wallpapersbq.com/images/300/300-wallpaper-9.jpg"))
//                .setPosterArtUri(Uri.parse("https://wallpapersbq.com/images/300/300-wallpaper-9.jpg"))
//                .setDescription("Program description $i")
//                .setLogoUri(Uri.parse("https://images-eu.ssl-images-amazon.com/images/I/51CNksbNcfL.png"))
//
//            contentResolver?.insert(
//                TvContractCompat.Programs.CONTENT_URI,
//                programBuilder1.build().toContentValues())
//        }
//
//        TvContractCompat.requestChannelBrowsable(this, channelId)
//    }
//
//    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//    private fun createChannels(number:Int): Uri {
//        val builder = Channel.Builder()
//// Every channel you create must have the type TYPE_PREVIEW
//        builder.setType(TvContractCompat.Channels.TYPE_PREVIEW)
//            .setDisplayName("Channel $number")
//            .setAppLinkIconUri(
//                Uri.parse(
//                    "https://logos-download.com/wp-content/uploads/2016/11/Deutsche_Telekom_logo_pink.png"))
//            .setInputId(1.toString())
//
//        return contentResolver.insert(TvContract.Channels.CONTENT_URI, builder.build().toContentValues())!!
//    }

//    @SuppressLint("StaticFieldLeak")
//    inner class ContentTask: AsyncTask<String, String, String>(){
//        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//        override fun doInBackground(vararg params: String?): String {
//            for(i in 1..10) {
//                createChannelPrograms(createChannels(i))
//            }
//            return "completed"
//        }
//
//        override fun onPostExecute(result: String?) {
//            Toast.makeText(applicationContext,"Channels and programs created", Toast.LENGTH_SHORT).show()
//        }
//    }
}
