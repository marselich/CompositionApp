package ru.kalievmars.compositionapp.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kalievmars.domain.entities.Level
import java.lang.RuntimeException

class MainViewModelFactory(
    private val application: Application,
    private val level: Level
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application, level) as T
        }
        throw RuntimeException("Unknown view model $modelClass")
    }
}