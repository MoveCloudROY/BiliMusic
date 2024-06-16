package top.roy1994.bilimusic

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import top.roy1994.bilimusic.data.objects.biliapi.BiliService
import top.roy1994.bilimusic.data.objects.biliapi.BiliCreator
import top.roy1994.bilimusic.data.utils.BiliRepo
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class BiliRepoTest {

    lateinit var biliRepo: BiliRepo
    lateinit var service: BiliService

    @Before
    fun create() {
        service = BiliCreator.getServiceInstance()
        biliRepo = BiliRepo(service)
    }

    @After
    @Throws(IOException::class)
    fun delete() {
    }

    @Test
    @Throws(Exception::class)
    fun GetCid() = runBlocking {
        val nBvid = "BV1rp4y1e745"
        val cid = biliRepo.getCid(nBvid).await()
        assertEquals(244954665, cid)
    }

    @Test
    @Throws(Exception::class)
    fun GetCoverUrl() = runBlocking {
        val nBvid = "BV13e4y1L7EG"
        val url = biliRepo.getCoverUrl(nBvid)
        assertNotEquals(null, url)
        println("===================================")
        println("Success to get the cover url: $url")
        println("===================================")
    }

    @Test
    @Throws(Exception::class)
    fun GetMusicUrl() = runBlocking {
        val nBvid = "BV13e4y1L7EG"
        val url = biliRepo.getMusicUrl(nBvid).await()
        assertNotEquals(null, url)
        println("===================================")
        println("Success to get the music url: $url")
        println("===================================")
    }
}
