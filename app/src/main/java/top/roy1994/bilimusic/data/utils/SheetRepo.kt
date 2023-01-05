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
    val size: LiveData<Int> = sheetDao.count()

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

    fun findSheetByName(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindByName(name).await()
        }
    }


    private fun asyncFindByName(name: String): Deferred<List<SheetEntity>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async sheetDao.findSheetByName(name)
        }

    fun findSheetById(id: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindById(id).await()
        }
    }

    private fun asyncFindById(id: Int): Deferred<List<SheetEntity>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async sheetDao.findSheetById(id)
        }
}