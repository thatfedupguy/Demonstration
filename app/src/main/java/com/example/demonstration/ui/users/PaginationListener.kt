package com.example.demonstration.ui.users

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class PaginationListener @Inject constructor(
    private val linearLayoutManager: LinearLayoutManager
): RecyclerView.OnScrollListener() {

    private lateinit var paginationCallbacks: PaginationCallbacks

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItemCount = linearLayoutManager.itemCount
        val lastItemIndex = linearLayoutManager.findLastCompletelyVisibleItemPosition()
        if (totalItemCount >= 10 && lastItemIndex + 1 == totalItemCount) {
            paginationCallbacks.loadMoreItems()
        }
    }

    fun setPaginationCallbacks(paginationCallbacks: PaginationCallbacks) {
        this.paginationCallbacks = paginationCallbacks
    }
}