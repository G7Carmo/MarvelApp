package com.gds.marvelapp.di

import com.gds.marvelapp.data.remote.ServiceApi
import com.gds.marvelapp.util.Constantes
import com.gds.marvelapp.util.Constantes.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MarvelAppModulo {

    @Singleton
    @Provides
    fun providerOkHttpClient() : OkHttpClient{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient().newBuilder()
            .addInterceptor{chain->
                val currentyTimeStamp = System.currentTimeMillis()
                val newUrl = chain.request().url
                    .newBuilder()
                    .addQueryParameter(Constantes.TS,currentyTimeStamp.toString())
                    .addQueryParameter(Constantes.APIKEY ,Constantes.PUBLIC_KEY)
                    .addQueryParameter(Constantes.HASH,
                        providerToMd5Hash(currentyTimeStamp.toString() + Constantes.PRIVATE_KEY + Constantes.PUBLIC_KEY))
                    .build()


                val newRequest = chain.request()
                    .newBuilder()
                    .url(newUrl)
                    .build()
                chain.proceed(newRequest)
            }
            .addInterceptor(logging)
            .build()
    }
    @Singleton
    @Provides
    fun providerRetrofit(client: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    @Singleton
    @Provides
    fun providerServiceApi(retrofit: Retrofit): ServiceApi {
        return retrofit.create(ServiceApi::class.java)
    }
    @Singleton
    @Provides
    fun providerToMd5Hash(encrypted: String) : String{
        var pass = encrypted
        var encryptedString : String? = null
        val md5 : MessageDigest
        try {
            md5 = MessageDigest.getInstance("MD5")
            md5.update(pass.toByteArray(),0,pass.length)
            pass = BigInteger(1,md5.digest()).toString(16)
            while (pass.length < 32){
                pass = "0$pass"
            }
            encryptedString = pass
        }catch (e1 : NoSuchAlgorithmException){
            e1.printStackTrace()
        }
        Timber.d("hash -> $encryptedString")
        return encryptedString ?: ""
    }





}