package com.dongbin.data.security

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jdk.internal.net.http.common.Log
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Date
import java.util.Locale
import javax.crypto.Cipher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EncryptionModule {
    private var publicKeyString: String = System.getenv("avsecret") ?: "NO_KEY"
    val publicKeyBytes = Base64.getDecoder().decode(publicKeyString)

    @Provides
    @Singleton
    fun provideEncryptionKey(): String {
        print(publicKeyString)
        val requestTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(Date())
        val data = requestTime.toByteArray(StandardCharsets.UTF_8)

        // RSA 공개 키 생성
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = X509EncodedKeySpec(publicKeyBytes)
        val rsaPublicKey: PublicKey = keyFactory.generatePublic(keySpec)

        // RSA 암호화 초기화
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding") // 동일한 패딩 사용
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey)

        // 데이터 암호화
        val encryptedBytes = cipher.doFinal(data)

        // 암호화된 데이터를 Base64로 인코딩
        val encryptedString = Base64.getEncoder().withoutPadding().encodeToString(encryptedBytes)

        return encryptedString
    }
}