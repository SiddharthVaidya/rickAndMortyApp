package com.example.rickandmortyapp



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity(){

    private lateinit var pager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabs)



        val fragmentAdapter = ViewPagerAdapter(supportFragmentManager)

        fragmentAdapter.addFragment(AllCharacters(), "All Characters")
        fragmentAdapter.addFragment(Groups(), "Group By")
        fragmentAdapter.addFragment(Sort(), "Sort By")
        pager.adapter = fragmentAdapter

        tabLayout.setupWithViewPager(pager)

    }

}





