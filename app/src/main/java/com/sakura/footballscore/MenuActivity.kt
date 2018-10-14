package com.sakura.footballscore

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                showFragment("lastEvent")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                showFragment("nextEvent")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                showFragment("favorit")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        supportActionBar?.title = "Football Match"

        showFragment("lastEvent")
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun showFragment(tag : String?) {

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentFragment, MatchFragment.newInstance(), tag)
                .commit()
    }


}
