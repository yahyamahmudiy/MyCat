package com.example.mycat.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mycat.Adapter.SearchAdapter
import com.example.mycat.Helper.SpaceItemDecoration
import com.example.mycat.Model.BreedsItem
import com.example.mycat.Model.ResponseItem
import com.example.mycat.Networking.RetrofitHttp
import com.example.mycat.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var manager: StaggeredGridLayoutManager
    lateinit var adapter: SearchAdapter
    private var list =  ArrayList<BreedsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        initViews(view)

        return view
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerViewBreeds)
        manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val decoration = SpaceItemDecoration(10)
        recyclerView.addItemDecoration(decoration)
        recyclerView.layoutManager = manager

        apiGetPhotosRetrofit()
    }

    private fun apiGetPhotosRetrofit(){
        RetrofitHttp.apiService.getAll().enqueue(object : Callback<ArrayList<BreedsItem>>{
            override fun onResponse(call: Call<ArrayList<BreedsItem>>, response: Response<ArrayList<BreedsItem>>) {
                Log.d("@@@", "onResponseSearch: ${response.body()}")
                list.clear()
                list.addAll(response.body()!!)
                refreshAdapter(list)
            }

            override fun onFailure(call: Call<ArrayList<BreedsItem>>, t: Throwable) {
                Log.d("@@@", "onFailureSearch: ${t.localizedMessage}")
            }

        })
    }

    fun refreshAdapter(list: ArrayList<BreedsItem>){
        adapter = SearchAdapter(list)
        recyclerView.adapter = adapter
    }

}