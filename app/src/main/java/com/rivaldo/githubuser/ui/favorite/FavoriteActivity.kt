package com.rivaldo.githubuser.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivaldo.githubuser.databinding.ActivityFavoriteBinding
import com.rivaldo.githubuser.model.USER_KEY
import com.rivaldo.githubuser.ui.detail.DetailActivity
import com.rivaldo.githubuser.ui.favorite.viewmodel.FavoriteViewModel
import com.rivaldo.githubuser.ui.main.adapter.ListUserAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModel()
    private val adapterRV: ListUserAdapter by lazy { ListUserAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getListUser()
        initRecyclerView()
        observeData()
        supportActionBar?.title = "Favorite User"
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        adapterRV.setListUser(viewModel.listUser.value)
        adapterRV.setOnItemClick { selectedUser ->
            // Todo : Open Detail Activity
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(USER_KEY, selectedUser)
            startActivity(intent)
        }
        binding.rvFavorite.layoutManager = layoutManager
        binding.rvFavorite.adapter = adapterRV
    }

    private fun observeData() {
        viewModel.viewModelScope.launch {
            viewModel.listUser.collect {
                adapterRV.setListUser(it)
                adapterRV.notifyDataSetChanged()
            }
        }

    }
}