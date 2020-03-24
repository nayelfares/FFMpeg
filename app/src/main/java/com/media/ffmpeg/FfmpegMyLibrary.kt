package com.media.ffmpeg

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.ProgressBar
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.FFmpegLoadBinaryResponseHandler
import java.lang.Exception

class FfmpegMyLibrary (val context: Context){
    var fFmpeg: FFmpeg?=null
    init {
        loadFfmpeg()
    }

    private fun loadFfmpeg(){
        if (fFmpeg==null){
            fFmpeg= FFmpeg.getInstance(context)
            fFmpeg!!.loadBinary(object : FFmpegLoadBinaryResponseHandler {
                override fun onFinish() {}
                override fun onSuccess() {}
                override fun onFailure() {}
                override fun onStart() {}
            })
        }
    }

    fun createVideoFromImage(inputFileUri:Uri, destFilePath: String,progressBar: ProgressBar,secondDuration:Int) {
        var empty = arrayOf("-framerate","1","-loop","1","-i",getPath(context,inputFileUri),"-c:v","libx264","-t","$secondDuration","-pix_fmt","yuv420p", "-vf","scale=240:240",destFilePath)
        fFmpeg!!.execute(empty,object: ExecuteBinaryResponseHandler() {
            override fun onFinish() {
                super.onFinish()
                progressBar.visibility= View.GONE
            }

            override fun onStart() {
                super.onStart()
                progressBar.visibility= View.VISIBLE
            }
        })
    }

    private fun getPath(context: Context, uri: Uri):String{
        try {
            var prog = arrayOf(MediaStore.Video.Media.DATA)
            var cursor = context.contentResolver.query(uri, prog, null, null, null)
            var culomn_index = cursor!!.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor!!.moveToFirst()
            return cursor.getString(culomn_index)
        }catch (e:Exception){
            return ""
        }
    }

}