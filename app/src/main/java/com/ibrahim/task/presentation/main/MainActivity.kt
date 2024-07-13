package com.ibrahim.task.presentation.main

import android.os.Bundle
import com.ibrahim.task.R
import com.ibrahim.task.databinding.ActivityMainBinding
import com.ibrahim.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>({ActivityMainBinding.inflate(it)}) {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        setUpNavigator(R.id.mainContainer,this)
    }
}