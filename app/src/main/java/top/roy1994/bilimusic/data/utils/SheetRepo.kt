package top.roy1994.bilimusic.data.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import top.roy1994.bilimusic.data.objects.sheet.SheetDao
import top.roy1994.bilimusic.data.objects.sheet.SheetEntity

class SheetRepo(
    private val sheetDao: SheetDao,
) {
    val allSheets: LiveData<List<SheetEntity>> = sheetDao.loadAllSheets()
    val searchResults = MutableLiveData<List<SheetEntity>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    fun insertSheet(newMusic: SheetEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            sheetDao.insertSheets(newMusic)
        }
    }

    fun deleteSheet(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            sheetDao.deleteSheetByName(name)
        }
    }

    fun findSheet(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }

    private fun asyncFind(name: String): Deferred<List<SheetEntity>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async sheetDao.findSheetByName(name)
        }
}