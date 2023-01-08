package top.roy1994.bilimusic.data.objects.biliapi

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BiliService {
    @Headers("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.54","Referer: https://www.bilibili.com/video/{bvid}")
    @GET("/x/player/playurl")
    suspend fun getData(
        @Query("bvid")      bvid: String,
        @Query("cid")       cid: String,
//        @Query("qn")        qn: Int = 32,               //dash方式无效
        @Query("fnval")     fnval: Int = 16,          //dash方式（音视频分流，支持H.265）16 80
//        @Query("fnver")     fnver: Int = 0,
//        @Query("fourk")     fourk: Int = 0,
//        @Query("session")   session: String = "",       //从视频播放页的网页源码中获取,非必要
//        @Query("otype")     otype: String = "json",
//        @Query("type")      type: String = "",
//        @Query("platform")  platform: String = "pc",    //非必要,默认为pc
    ): Response<PlayUrlRoot>

    @Headers("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.54","Referer: https://www.bilibili.com/video/{bvid}")
    @GET("/x/player/pagelist")
    suspend fun getCid(
        @Query("bvid") bvid: String,
    ): Response<PageListRoot>

    @Headers("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.54","Referer: https://www.bilibili.com/video/{bvid}")
    @GET("/x/web-interface/view")
    suspend fun getVideoInfo(
        @Query("bvid") bvid: String,
    ): Response<VideoInfo>
}