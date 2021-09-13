package com.example.daydayweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import com.example.daydayweather.view.ViewPagerFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolBar= findViewById<Toolbar>(R.id.tbMainFragment)
        setSupportActionBar(toolBar)
        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, ViewPagerFragment())
        }

    }
}



