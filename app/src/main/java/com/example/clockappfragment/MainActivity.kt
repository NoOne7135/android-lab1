package com.example.clockappfragment

import ClockFragment
import SettingFragment
import android.net.wifi.rtt.CivicLocationKeys
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.clockappfragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val dataModel: DataModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        showSplashScreen()
        Handler(Looper.getMainLooper()).postDelayed({
            setContentView(binding.root)
            removeSplashScreen()
            openFragment(binding.placeHolder.id, ClockFragment.newInstance())
        }, 2000)

        binding.BSetting.setOnClickListener { openFragment(binding.placeHolder.id, SettingFragment.newInstance()) }
        binding.BTimer.setOnClickListener { openFragment(binding.placeHolder.id, TimerFragment.newInstance()) }
        binding.BClock.setOnClickListener { openFragment(binding.placeHolder.id, ClockFragment.newInstance()) }
        dataModel.timeZone.observe(this, {

        })
    }

    private fun showSplashScreen() {
        val splashFragment = SplashFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, splashFragment)
            .commit()
    }
    private fun removeSplashScreen() {
        val splashFragment = SplashFragment.newInstance()
        supportFragmentManager.beginTransaction().remove(splashFragment).commit()
    }
    private fun openFragment(id: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(id, fragment)
            .commit()
    }

}