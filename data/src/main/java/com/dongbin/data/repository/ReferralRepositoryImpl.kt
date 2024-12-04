package com.dongbin.data.repository

import com.dongbin.data.api.VersionApi
import com.dongbin.domain.repository.ReferralRepository
import javax.inject.Inject

class ReferralRepositoryImpl @Inject constructor(
    private val api: VersionApi
): ReferralRepository {
    override suspend fun fetchReferral(code: String): String {
        val response = api.fetchReferral(code)
        return if (response.isSuccessful) {
            response.body() ?: "불러오기 실패"
        } else {
            "불러오기 실패"
        }
    }
}