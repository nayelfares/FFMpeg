package com.media.ffmpeg

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import com.video.trimmer.interfaces.OnTrimVideoListener
import com.video.trimmer.interfaces.OnVideoListener
import kotlinx.android.synthetic.main.activity_share_to_snapchat.*
import java.io.File

class ShareToSnapchat : AppCompatActivity(), OnTrimVideoListener, OnVideoListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_to_snapchat)
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        var uri=intent.getStringExtra("uri")
        videoTrimmer.setTextTimeSelectionTypeface(Typeface.DEFAULT)
            .setOnTrimVideoListener(this)
            .setOnVideoListener(this)
            .setVideoURI(Uri.parse(uri))
            .setVideoInformationVisibility(true)
            .setMaxDuration(10)
            .setMinDuration(2)
            .setDestinationPath(filesDir.path)
        share.setOnClickListener {
            videoTrimmer.onSaveClicked()
        }
    }

    override fun cancelAction() {
        Log.e("","")
    }

    override fun getResult(uriNew: Uri) {
        progressBar.visibility= View.GONE
        var intent = Intent()
        intent.setAction(Intent.ACTION_SEND);
        intent.type = "video/*"
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        var uri = FileProvider.getUriForFile(this, "com.media.shaadoow", File(uriNew.path))
        grantUriPermission(
            packageName,
            uri,
            Intent.FLAG_GRANT_WRITE_URI_PERMISSION and Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.setPackage("com.snapchat.android")
        startActivity(intent)
        finish()
    }

    override fun onError(message: String) {
        Log.e("","")
    }

    override fun onTrimStarted() {
        progressBar.visibility= View.VISIBLE
    }

    override fun onVideoPrepared() {
        Log.e("","")
    }

}
