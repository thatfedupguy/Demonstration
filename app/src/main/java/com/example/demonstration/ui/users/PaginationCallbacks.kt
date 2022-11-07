package com.example.demonstration.ui.users

interface PaginationCallbacks {
    fun loading(): Boolean
    fun loadMoreItems()
}