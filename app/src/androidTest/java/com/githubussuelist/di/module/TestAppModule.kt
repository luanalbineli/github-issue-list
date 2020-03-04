package com.githubussuelist.di.module

import android.content.Context
import androidx.room.Room
import com.githubussuelist.BuildConfig
import com.githubussuelist.repository.room.GitHubIssueDB
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object TestAppModule {
    @Provides
    @JvmStatic
    @Singleton
    fun provideRoomDataBase(context: Context): GitHubIssueDB
        = Room.inMemoryDatabaseBuilder(context, GitHubIssueDB::class.java).build()

    @Provides
    @JvmStatic
    @Reusable
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return gsonBuilder.create()
    }

    @Provides
    @JvmStatic
    @Reusable
    fun providesHttpClientBuilder(): OkHttpClient.Builder {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .method(original.method, original.body)

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        httpClient.connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        return httpClient
    }

    @Provides
    @JvmStatic
    @Reusable
    fun providesDefaultRetrofitInstance(okHttpClientBuilder: OkHttpClient.Builder, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClientBuilder.build())
            .build()
    }
}