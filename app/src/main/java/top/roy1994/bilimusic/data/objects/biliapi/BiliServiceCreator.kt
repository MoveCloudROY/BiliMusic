package top.roy1994.bilimusic.data.objects.biliapi

import android.content.Context
import androidx.room.Room
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BiliServiceCreator {
    private const val BASE_URL = "http://api.bilibili.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
    inline fun <reified T> create(): T = create(T::class.java)


    private var INSTANCE: BiliService? = null
    fun getInstance(): BiliService {
        synchronized(this) {
            var instance = INSTANCE

            if (instance == null) {
                instance = create<BiliService>()
                INSTANCE = instance
            }
            return instance
        }
    }
}