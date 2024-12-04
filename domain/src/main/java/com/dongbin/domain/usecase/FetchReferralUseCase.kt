package com.dongbin.domain.usecase

import com.dongbin.domain.repository.ReferralRepository
import javax.inject.Inject

class FetchReferralUseCase @Inject constructor(
    private val repository: ReferralRepository
) {
    suspend operator fun invoke(code: String): String {
        return repository.fetchReferral(code)
    }
}