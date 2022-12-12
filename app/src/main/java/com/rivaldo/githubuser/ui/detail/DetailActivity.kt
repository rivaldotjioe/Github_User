package com.rivaldo.githubuser.ui.detail

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rivaldo.githubuser.R
import com.rivaldo.githubuser.data.Resource
import com.rivaldo.githubuser.data.remote.model.ResponseDetailUser
import com.rivaldo.githubuser.databinding.ActivityDetailBinding
import com.rivaldo.githubuser.model.USER_KEY
import com.rivaldo.githubuser.model.User
import com.rivaldo.githubuser.ui.detail.fragment.SectionsPagerAdapter
import com.rivaldo.githubuser.ui.detail.viewmodel.DetailViewModel
import com.rivaldo.githubuser.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var user: User
    lateinit var detailUser: ResponseDetailUser
    private val viewModel: DetailViewModel by viewModel()
    private var isFavorite = MutableLiveData<Boolean>()
    var _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var isLoading: StateFlow<Boolean> = _isLoading

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.content_tab_follower,
            R.string.content_tab_following
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = intent.extras?.getParcelable(USER_KEY, User::class.java) as User
        observeIsFavorite()
        observeIsLoading()
        checkIsFavorite()
        getDetailUser()
        initializeViewPager()
//        setDetailUserData()
        favoriteButtonClickAction()
    }

    private fun initializeViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, user.username)
        val viewPager : ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])

        }.attach()
    }

    private fun checkIsFavorite() {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            isFavorite.postValue(viewModel.checkIsFavorite(user.username))
        }
    }

    private fun observeIsLoading() {
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            isLoading.collect {
                if (it) {
                    binding.progressBar2.visibility = View.VISIBLE
                } else {
                    binding.progressBar2.visibility = View.GONE
                }
            }

        }
    }

    private fun getDetailUser() {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            viewModel.getDetailUser(user.username).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }
                    is Resource.Success -> {
                        result.data?.let {
                            detailUser = it
                            _isLoading.emit(false)
                            runOnUiThread { setDetailUserData() }

                        }
                    }
                    is Resource.Error -> {
                        _isLoading.emit(false)
                    }
                }
            }
        }
    }

    private fun setDetailUserData() {
        binding.apply {
            if (user.avatarInt != null) {
                circleImageView2.setImageResource(user.avatarInt?: R.drawable.user1)
            } else {
                Glide.with(this@DetailActivity).load(user.avatar).into(circleImageView2)
            }
            txtNameDetail.text = detailUser.name
            txtUsernameDetail.text = detailUser.login
            txtFollowerDetail.text = detailUser.followers.toString()
            txtFollowingDetail.text = detailUser.following.toString()
            txtRepositoryDetail.text = detailUser.publicRepos.toString()
        }
    }

    private fun observeIsFavorite() {
        isFavorite.observe(this) {
            if (it) {
                binding.favoriteFab.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                binding.favoriteFab.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }
    }

    private fun favoriteButtonClickAction() {
        binding.favoriteFab.setOnClickListener {
            if (isFavorite.value == true) {
                viewModel.deleteUserFavorite(DataMapper.mapUserToUserEntity(user))
                isFavorite.value = false
            } else {
                viewModel.insertUserFavorite(DataMapper.mapUserToUserEntity(user))
                isFavorite.value = true
            }
        }
    }
}