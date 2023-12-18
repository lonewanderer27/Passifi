package com.ja_cabili.passifi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ja_cabili.passifi.repository.Repository

class MainViewModelFactory (
    private val repository: Repository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}