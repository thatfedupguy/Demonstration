package com.example.demonstration.di

import android.content.Context
import com.example.demonstration.data.db.AppDatabase
import com.example.demonstration.data.db.UserDao
import com.example.demonstration.data.remote.UsersApi
import com.example.demonstration.data.remote.UsersApiHelper
import com.example.demonstration.ui.users.UsersApiHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient  = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun providesRetrofitClient(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.github.com/")
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideUsersApi(retrofit: Retrofit) = retrofit.create(UsersApi::class.java)

    @Singleton
    @Provides
    fun providesUserApiHelperImpl(usersApiHelperImpl: UsersApiHelperImpl): UsersApiHelper = usersApiHelperImpl

    @Singleton
    @Provides
    fun provideUserDao(@ApplicationContext context: Context): UserDao = AppDatabase(context).getUserDao()
}