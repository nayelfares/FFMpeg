package com.media.ffmpeg


import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {
    val GALLERY=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pickVideo.setOnClickListener {
            var int=Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(int,GALLERY)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==GALLERY)
            if (data?.data != null) {
                var folder= File(Environment.getExternalStorageDirectory().toString()+"/trimVideos")
                if (!folder.exists())
                    folder.mkdir()
                val dest= File(folder,"ssss"+".mp4")
                intent.putExtra("uri",data.data.toString())
                FfmpegMyLibrary(this).createVideoFromImage(data.data!!,dest.path,progressBar,55)
            }
    }
}
