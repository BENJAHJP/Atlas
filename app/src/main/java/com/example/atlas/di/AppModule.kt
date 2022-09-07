package com.example.atlas.di

import com.example.atlas.constants.Urls
import com.example.atlas.data.PostApi
import com.example.atlas.data.PostRepository
import com.example.atlas.data.PostRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePostApi(): PostApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Urls.BASE_URL)
            .build()
            .create(PostApi::class.java)
    }


    @Singleton
    @Provides
    fun providePostRepository(postApi: PostApi): PostRepository{
        return PostRepositoryImpl(postApi)
    }
}