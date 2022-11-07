package com.example.demonstration.ui.users

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.demonstration.databinding.FragmentUsersBinding
import com.example.demonstration.models.User
import com.example.demonstration.utils.ProgressStatus
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UsersFragment : Fragment(), PaginationCallbacks {

    companion object {
        const val PER_PAGE = "10"
    }

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UsersViewModel by viewModels()
    private val users: MutableList<User> = arrayListOf()
    private lateinit var usersAdapter: UsersAdapter
    var since = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObservers()
        fetchUsers()
    }

    private fun fetchUsers() {
        if(isNetworkAvailable()){
            viewModel.getUsers(since.toString(), PER_PAGE)
        } else {
            viewModel.getUsersFromDb()
        }
    }

    private fun initRecyclerView() {
        usersAdapter = UsersAdapter( onUserClicked = {
            val action = UsersFragmentDirections.actionUsersFragmentToRepositoryFragment(it!!)
            findNavController().navigate(action)
        })
        val layoutManager = GridLayoutManager(activity,  1)
        layoutManager.apply {
            val paginationListener = PaginationListener(this)
            paginationListener.setPaginationCallbacks(this@UsersFragment)
            binding.rvUsers.addOnScrollListener(paginationListener)
        }
        binding.rvUsers.apply {
            this.layoutManager = layoutManager
            this.adapter = usersAdapter
            this.setHasFixedSize(true)
        }
    }

    private fun initObservers() {
        viewModel.usersResponse.observe(viewLifecycleOwner) {
            handleUsersResponse(it)
        }
    }

    private fun handleUsersResponse(it: ProgressStatus<List<User>>?) = when (it) {
        is ProgressStatus.Loading -> {
            if(users.isEmpty()) binding.progressBar.visibility = View.VISIBLE
            else {
                // do nothing
            }
        }
        is ProgressStatus.DataSuccess -> {
            binding.progressBar.visibility = View.GONE
            binding.rvProgressBar.visibility = View.GONE
            if (it.data != null && !users.containsAll(it.data)) {
                users.addAll(it.data)
                usersAdapter.submitList(users)
                since = it.data.last().id!!
            } else {
                // do nothing
            }
        }
        is ProgressStatus.DataError -> {
            binding.progressBar.visibility = View.GONE
            binding.rvProgressBar.visibility = View.GONE
            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }
        else -> {
            // do nothing
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun loading(): Boolean {
        return binding.rvProgressBar.isVisible
    }

    override fun loadMoreItems() {
        binding.rvProgressBar.visibility = View.VISIBLE
        viewModel.getUsers(since.toString(), PER_PAGE)
    }
}