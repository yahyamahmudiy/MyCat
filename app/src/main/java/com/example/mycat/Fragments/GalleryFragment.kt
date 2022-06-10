package com.example.mycat.Fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.mycat.Model.ResponseItem
import com.example.mycat.Networking.ApiClient
import com.example.mycat.Networking.ApiService
import com.example.mycat.R
import com.google.android.material.button.MaterialButton
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class GalleryFragment : Fragment() {
    lateinit var button: MaterialButton
    lateinit var imageView: ImageView

    private lateinit var apiService: ApiService
    lateinit var fileUri: Uri
    lateinit var file: File
    val PICK_IMAGE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiService = ApiClient(requireContext()).createServiceWithAuth(ApiService::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view = inflater.inflate(R.layout.fragment_gallery, container, false)

        initViews(view)

       return view
    }

    private fun initViews(view: View) {
        imageView = view.findViewById(R.id.imageView)
        button = view.findViewById(R.id.b_upload)

        imageView.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    listOf(Manifest.permission.READ_EXTERNAL_STORAGE).toTypedArray(),
                    2000
                )
            } else {
                startGallery()
            }
        }

        button.setOnClickListener {

            val reqFile: RequestBody = RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
            val body: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, reqFile)

            apiService.uploadFile(body, "sub_idjjhdbid").enqueue(object : Callback<ResponseItem> {
                override fun onResponse(call: Call<ResponseItem>, response: Response<ResponseItem>) {
                    Log.d("TAG", "onResponse: ${response.body()}")
                }

                override fun onFailure(call: Call<ResponseItem>, t: Throwable) {
                    Log.d("TAG", "onFailure: $t")
                }

            })

        }
    }

    private fun startGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            fileUri = data?.data!!

            val ins = requireActivity().contentResolver.openInputStream(fileUri)
            file = File.createTempFile(
                "file",
                ".jpg",
                requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            )

            val fileOutputStream = FileOutputStream(file)

            ins?.copyTo(fileOutputStream)
            ins?.close()
            fileOutputStream.close()

            if (file.length() != 0L) {
                Glide.with(requireContext())
                    .load(file)
                    .into(imageView)
            }

            button.visibility = View.VISIBLE
        }

    }
}