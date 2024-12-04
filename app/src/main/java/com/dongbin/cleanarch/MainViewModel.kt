package com.dongbin.cleanarch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dongbin.domain.usecase.FetchReferralUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchReferralUseCase: FetchReferralUseCase
): ViewModel() {

    private val _appVersion = MutableStateFlow("")
    val appVersion: StateFlow<String> = _appVersion

    fun fetchAppVersion(os: String) {
        viewModelScope.launch {
            try {
                val result = fetchReferralUseCase(os)
                _appVersion.value = result
            } catch (e: Exception) {
                _appVersion.value = "불러오기 실패"
            }
        }
    }
}