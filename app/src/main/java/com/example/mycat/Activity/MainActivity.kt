package com.example.mycat.Activity

import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mycat.Fragments.GalleryFragment
import com.example.mycat.Fragments.PhotosFragment
import com.example.mycat.Fragments.SearchFragment
import com.example.mycat.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        /*val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        bottomNavigationView.setupWithNavController(navHostFragment.navController)


        val firstFragment= SearchFragment()
        val secondFragment=PhotosFragment()

        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.searchFragment->setCurrentFragment(firstFragment)
                R.id.photosFragment->setCurrentFragment(secondFragment)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout,fragment)
            addToBackStack("back")
            commit()
        }*/

        bottomNavigationView.background = null

        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, SearchFragment()).commit()
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var temp: Fragment? = null
            when (item.itemId) {
                R.id.searchFragment -> temp = SearchFragment()
                R.id.photosFragment -> temp = PhotosFragment()

            }
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout, temp!!).commit()
            true
        }

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingactionbutton)
        floatingActionButton.setOnClickListener {
            //val intent = Intent(this, GellereyActivity::class.java)
            //startActivity(intent)
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout, GalleryFragment()).commit()

        }

    }
    fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            addToBackStack("back")
            commit()
        }
    }
}