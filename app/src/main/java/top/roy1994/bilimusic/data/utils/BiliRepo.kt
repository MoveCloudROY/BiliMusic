package top.roy1994.bilimusic.data.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import top.roy1994.bilimusic.data.objects.biliapi.BiliService

class BiliRepo(
    val service: BiliService
) {
//    private val service = BiliServiceCreator.getInstance()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val responseCid: MutableLiveData<Int> = MutableLiveData()
    val responseCoverUrl: MutableLiveData<String> = MutableLiveData()
    val responseMusicUrl: MutableLiveData<String> = MutableLiveData()

    fun getCid(bid: String): Deferred<Int?> {
        val deferred = coroutineScope.async(Dispatchers.IO) {
            val res = service.getCid(bid)
            var ret: Int? = null
            withContext(Dispatchers.Main) {
                if (res.isSuccessful) {
                    responseCid.value = res.body()?.data?.get(0)?.cid
                    ret = responseCid.value
                } else {
                    Log.e("BiliRepo", "Failed to get CID")
                }
            }
            ret
        }
        return deferred
    }
    suspend fun getCoverUrl(bid: String) =
        withContext(Dispatchers.IO) {
            val res = service.getVideoInfo(bid)
            var ret: String? = null
            withContext(Dispatchers.Main) {
                if (res.isSuccessful) {
                    responseCoverUrl.value = res.body()?.data?.pic
                    ret = responseCoverUrl.value
                } else {
                    Log.e("BiliRepo-DATA", "Failed to get cover url")
                }
            }
            ret
        }

    fun getMusicUrl(bid: String, part: Int = 1): Deferred<String?> {
        val deferred = coroutineScope.async(Dispatchers.IO) {
            var ret: String? = null
            val resCid = service.getCid(bid).body()?.data?.get(0)?.cid?.toString()
            val resUrl = resCid?.let { service.getData(bvid = bid, cid = it) }

            if (resUrl != null) {
                withContext(Dispatchers.Main) {
                    if (resUrl.isSuccessful) {
                        responseMusicUrl.value = resUrl.body()?.data?.dash?.audio?.get(0)?.baseUrl
                        ret = responseMusicUrl.value
                    } else {
                        Log.e("BiliRepo", "Failed to get music url")
                    }
                }
            }
            ret
        }
        return deferred
    }

    fun getMusicInfo(bid: String): Deferred<Long?> =
        coroutineScope.async(Dispatchers.IO) {
            var ret: Long? = null
            val resCid = service.getCid(bid).body()?.data?.get(0)?.cid?.toString()
            val resUrl = resCid?.let { service.getData(bvid = bid, cid = it) }

            if (resUrl!=null && resUrl.isSuccessful) {
                ret = resUrl.body()?.data?.timelength?.toLong()?.div(1000)
            }
            return@async ret
        }


    data class MusicBase(
        var audio_url: String?,
        var seconds: Long?,
    )
}