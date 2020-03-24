package com.media.ffmpeg

import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.core.view.get
import com.video.trimmer.interfaces.OnTrimVideoListener
import com.video.trimmer.interfaces.OnVideoListener
import kotlinx.android.synthetic.main.activity_progress_bar_activty.*
import java.io.File

class ProgressBarActivty : AppCompatActivity(), OnTrimVideoListener,OnVideoListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_bar_activty)
        var uri=intent.getStringExtra("uri")
        var t=videoTrimmer.setTextTimeSelectionTypeface(Typeface.DEFAULT)
            .setOnTrimVideoListener(this)
            .setOnVideoListener(this)
            .setVideoURI(Uri.parse(uri))
            .setVideoInformationVisibility(true)
            .setMaxDuration(15)
            .setMinDuration(2)
            .setDestinationPath(Environment.getExternalStorageDirectory().toString() + File.separator +"trimVideos" + File.separator)
            showToast(t.get(0).toString())
         t.onSaveClicked()
    }

    override fun cancelAction() {
        TODO("Not yet implemented")
    }

    override fun getResult(uri: Uri) {
        showToast(uri.toString())
    }

    override fun onError(message: String) {
        showToast("Error")
    }

    override fun onTrimStarted() {
        showToast("started")
    }

    override fun onVideoPrepared() {

    }

    fun showToast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

}
