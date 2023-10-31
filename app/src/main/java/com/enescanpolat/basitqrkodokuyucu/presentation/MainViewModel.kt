package com.enescanpolat.basitqrkodokuyucu.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enescanpolat.basitqrkodokuyucu.domain.repository.ScanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
 private val repository: ScanRepository
):ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state =_state.asStateFlow()

    fun startScaning(){
        viewModelScope.launch {
            repository.startScaning().collect{

                if (!it.isNullOrBlank()){
                    _state.value=state.value.copy(
                        details = it
                    )
                }

            }
        }
    }
}