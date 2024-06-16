package top.roy1994.bilimusic.data.objects.biliapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import top.roy1994.bilimusic.data.utils.BiliRepo

object BiliCreator {
    private const val BASE_URL = "http://api.bilibili.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
    inline fun <reified T> create(): T = create(T::class.java)


    private var INSTANCE: BiliService? = null
    private var REPO_INSTANCE: BiliRepo? = null
    fun getServiceInstance(): BiliService {
        synchronized(this) {
            var instance = INSTANCE

            if (instance == null) {
                instance = create<BiliService>()
                INSTANCE = instance
            }
            return instance
        }
    }
    fun getInstance(): BiliRepo {
        synchronized(this) {
            var instance = REPO_INSTANCE;
            if (instance == null) {
                instance = BiliRepo(getServiceInstance())
                REPO_INSTANCE = instance
            }
            return instance
        }
    }
}