package com.rivaldo.githubuser.ui.detail.fragment.follower

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivaldo.githubuser.R
import com.rivaldo.githubuser.databinding.FragmentFollowerBinding
import com.rivaldo.githubuser.model.USER_KEY
import com.rivaldo.githubuser.ui.detail.DetailActivity
import com.rivaldo.githubuser.ui.main.adapter.ListUserAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var username: String? = null
    private var param2: String? = null

    lateinit var binding : FragmentFollowerBinding
    private val adapterRV: ListUserAdapter by lazy { ListUserAdapter() }
    private val viewModel: FollowerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        observeData()
        observeIsLoading()
        viewModel.getListFollower(username.toString())
        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this.activity)
        adapterRV.setListUser(viewModel.listUser.value)
        binding.rvFollower.layoutManager = layoutManager
        binding.rvFollower.adapter = adapterRV
    }

    private fun observeData() {
        viewModel.viewModelScope.launch {
            viewModel.listUser.collect {
                adapterRV.setListUser(it)
                adapterRV.notifyDataSetChanged()
            }
        }
    }

    private fun observeIsLoading(){
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            viewModel.isLoading.collect {
                binding.progressBar3.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param username Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FollowerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(username: String) =
            FollowerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, username)
                }
            }
    }
}