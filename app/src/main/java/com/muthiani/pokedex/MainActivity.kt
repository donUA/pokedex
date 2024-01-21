package com.muthiani.pokedex

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.muthiani.pokedex.databinding.ActivityMainBinding

class MainActivity: FragmentActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("KOIN", "")
    }
}