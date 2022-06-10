package com.example.mycat.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mycat.Adapter.PhotosAdapter
import com.example.mycat.Adapter.SearchAdapter
import com.example.mycat.Helper.SpaceItemDecoration
import com.example.mycat.Model.ResponseItem
import com.example.mycat.Networking.RetrofitHttp
import com.example.mycat.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PhotosFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var manager: StaggeredGridLayoutManager
    lateinit var adapter: PhotosAdapter
    private var list =  ArrayList<ResponseItem>()
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_photos, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val decoration = SpaceItemDecoration(10)
        recyclerView.addItemDecoration(decoration)
        recyclerView.layoutManager = manager

        apiGetPhotosRetrofit()
    }
    private fun apiGetPhotosRetrofit(){
        RetrofitHttp.apiService.getMyCats(page).enqueue(object : Callback<ArrayList<ResponseItem>> {
            override fun onResponse(call: Call<ArrayList<ResponseItem>>, response: Response<ArrayList<ResponseItem>>) {
                Log.d("@@@", "onResponsePhotos: ${response.body()}")
                list.addAll(response.body()!!)
                refreshAdapter(list)
            }

            override fun onFailure(call: Call<ArrayList<ResponseItem>>, t: Throwable) {
                Log.d("@@@", "onFailurePhotos: ${t.localizedMessage}")
            }

        })
    }

    fun refreshAdapter(list: ArrayList<ResponseItem>){
        adapter = PhotosAdapter(list)
        recyclerView.adapter = adapter
    }


}