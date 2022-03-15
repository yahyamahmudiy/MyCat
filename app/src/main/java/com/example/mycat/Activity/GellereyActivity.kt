package com.example.mycat.Activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.mycat.R
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter

class GellereyActivity : AppCompatActivity() {
    private var imageList : ArrayList<Uri>? = null
    lateinit var buttonUpload: Button
    lateinit var buttonMake: Button
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gellerey)

        initViews()
    }

    private fun initViews() {
        imageView = findViewById(R.id.imageView)
        buttonUpload = findViewById(R.id.button1)
        buttonMake = findViewById(R.id.button2)

        buttonUpload.setOnClickListener {
            openImagePicker()
        }

    }

    private fun openImagePicker() {
        FishBun.with(this).setImageAdapter(GlideAdapter())
            .setMinCount(1)
            .setMaxCount(10)
            .startAlbum()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            FishBun.FISHBUN_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK){
                    imageList = data?.getParcelableArrayListExtra(FishBun.INTENT_PATH)
                    setImages()
                }
            }
        }
    }

    private fun setImages() {
        if (imageList != null){
            for (it in imageList!!.indices){
                when(it){
                    0 -> imageView.setImageURI(imageList?.get(it))
                }
            }
        }
    }
}