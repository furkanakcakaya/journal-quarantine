package com.furkanakcakaya.journalquarantine.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModelProvider

class UpdateEntryViewModelFactory (var application: Application): ViewModelProvider.NewInstanceFactory() {
    override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
        return UpdateEntryViewModel(application) as T
    }
}