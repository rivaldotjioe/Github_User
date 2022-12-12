package com.rivaldo.githubuser.ui.detail.fragment

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rivaldo.githubuser.ui.detail.fragment.follower.FollowerFragment
import com.rivaldo.githubuser.ui.detail.fragment.following.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, val username: String) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FollowerFragment.newInstance(username = username)
            1 -> fragment = FollowingFragment.newInstance(username = username)
        }
        return fragment as Fragment
    }


}