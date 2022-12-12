package com.rivaldo.githubuser.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivaldo.githubuser.R
import com.rivaldo.githubuser.databinding.ActivityMainBinding
import com.rivaldo.githubuser.model.USER_KEY
import com.rivaldo.githubuser.ui.detail.DetailActivity
import com.rivaldo.githubuser.ui.favorite.FavoriteActivity
import com.rivaldo.githubuser.ui.main.adapter.ListUserAdapter
import com.rivaldo.githubuser.ui.main.viewmodel.MainViewModel
import com.rivaldo.githubuser.utils.SettingsPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private val adapterRV: ListUserAdapter by lazy { ListUserAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.setSettingsPreferences(SettingsPreferences.getInstance(dataStore))
        viewModel.getListUser(this)
        initRecyclerView()
        observeData()
        observeThemeSettings()
        observeLoading()
    }

    private fun observeThemeSettings() {
        viewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }
    }

    private fun observeLoading() {
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            viewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }

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
        binding.rvUser.layoutManager = layoutManager
        binding.rvUser.adapter = adapterRV
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.optionmenumain, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search by Username"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchUser(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null || viewModel.viewModelScope.isActive) {
                    viewModel.searchUser(newText.toString())
                }
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                // Todo : Open Favorite Activity
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.action_theme -> {
                // Todo : Change Theme
                viewModel.saveThemeSettings(!viewModel.isDarkModeActive)
            }
        }
        return true
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