package com.dongbin.domain.repository

interface ReferralRepository {
    suspend fun fetchReferral(os: String): String
}