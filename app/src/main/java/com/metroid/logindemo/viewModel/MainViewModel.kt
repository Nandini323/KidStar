package com.metroid.logindemo.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel:ViewModel() {
    private val _navigateTo = MutableStateFlow<Int?>(null)
    val navigateTo: StateFlow<Int?> = _navigateTo

    fun onItemClicked(itemId: Int) {
        _navigateTo.value = itemId
    }

    fun resetNavigation() {
        _navigateTo.value = null
    }

    fun FavourateClicked(itemId: Int){
        _navigateTo.value = itemId
    }
}