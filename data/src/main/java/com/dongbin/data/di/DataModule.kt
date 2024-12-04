package com.dongbin.data.di

import com.dongbin.data.api.VersionApi
import com.dongbin.data.repository.ReferralRepositoryImpl
import com.dongbin.data.security.EncryptionModule
import com.dongbin.domain.repository.ReferralRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private const val BASE_URL = "https://example.com/api/v1/"

    @Provides
    @Singleton
    fun provideVersionApi(): VersionApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideAuthHeaderClient())
        .build()
        .create(VersionApi::class.java)


    fun provideAuthHeaderClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
//            .addInterceptor(Interceptor { chain ->
//                val encryptedString = runBlocking {
//                    EncryptionModule.provideEncryptionKey()
//                }
//                val request = chain.request().newBuilder()
//                    .addHeader("AVSECRET", encryptedString)
//                    .addHeader("AVDEVICE", "Android NEW")
//                    .build()
//                chain.proceed(request)
//            })
            .build()

        return okHttpClient
    }

    @Provides
    @Singleton
    fun provideVersionRepository(api: VersionApi): ReferralRepository {
        return ReferralRepositoryImpl(api)

    }
}