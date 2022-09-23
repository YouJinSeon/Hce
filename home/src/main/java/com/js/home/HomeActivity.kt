package com.js.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.js.home.adapter.HomeAdapter
import com.js.home.databinding.ActivityHomeBinding
import com.js.home.ui.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initOnClick()
        initRecyclerView()
        viewModel.run {
            userListLiveData.observe(this@HomeActivity) {
                (binding.rvHome.adapter as HomeAdapter).setData(it)
            }
        }
    }

    private fun initOnClick() {
        binding.apply {
            btnAdd.setOnClickListener {
                viewModel.addUser(etName.text.toString())
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvHome.layoutManager = LinearLayoutManager(this)
        binding.rvHome.adapter = HomeAdapter()
    }
}