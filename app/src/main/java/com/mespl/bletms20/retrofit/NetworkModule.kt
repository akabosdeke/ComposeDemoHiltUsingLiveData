package com.mespl.bletms20.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mespl.bletms20.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideBaseUrl(): String {
        return "https://192.168.2.8:4432/api/TTSV2/"
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return getOkHttpClient()
    }


    @Provides
    fun provideRetrofit(
        baseUrl: String,
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Named("fcmBaseUrl")
    fun provideFcmBaseUel(): String {
        return "https://fcm.googleapis.com/v1/"
    }

    @Provides
    @Named("fcmRetrofit")
    fun provideFCMRetrofit(
        @Named("fcmBaseUrl")
        baseUrl: String,
        gson: Gson,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getOkHttpClient(): OkHttpClient {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

                    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an SSL socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }

            return OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true } // Trust all hostnames
                .addInterceptor(loggingInterceptor)
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }


    /*  fun getOkHttpClient(): OkHttpClient {
          val loggingInterceptor = HttpLoggingInterceptor()
          val okhttpClientBuilder = OkHttpClient.Builder()
          loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

          okhttpClientBuilder.addInterceptor(Interceptor { chain ->
              val original: Request = chain.request()
              val request: Request = original.newBuilder()
                  .build()
              chain.proceed(request)
          }).apply {


              val timeOutSec = 45
              okhttpClientBuilder.connectTimeout(timeOutSec.toLong(), TimeUnit.SECONDS)
              okhttpClientBuilder.readTimeout(timeOutSec.toLong(), TimeUnit.SECONDS)
              okhttpClientBuilder.writeTimeout(timeOutSec.toLong(), TimeUnit.SECONDS)
              okhttpClientBuilder.addInterceptor(loggingInterceptor)
              return okhttpClientBuilder.build()
          }
      }*/
}

