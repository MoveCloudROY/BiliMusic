package top.roy1994.bilimusic.viewmodel


import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import top.roy1994.bilimusic.data.objects.sheet.SheetDao
import top.roy1994.bilimusic.data.objects.sheet.SheetEntity
import top.roy1994.bilimusic.data.utils.AppDatabase

class AddSheetViewModel(application: Application): AndroidViewModel(application) {
    private val sheetDao: SheetDao

    init {
        val appDb = AppDatabase.getInstance(application)
        sheetDao = appDb.sheetDao()
    }

    var name = mutableStateOf("")
    var description = mutableStateOf("")

    var addSuccess = mutableStateOf(false)
        private set

    fun addSheet() {
        viewModelScope.launch(Dispatchers.IO) {
            val sheets: List<SheetEntity> =
                    sheetDao.findSheetByName(name.value)
            if (sheets.isEmpty()) {
                sheetDao.insertSheets(
                    SheetEntity(
                        name = name.value,
                        description = description.value,
                    )
                )
            }
            name.value = ""
            description.value = ""
        }
        addSuccess.value = true

    }

    /**
     * 更新分类下标
     *
     * @param _name
     */
    fun updateName(_name: String) {
        name.value = _name
    }
    /**
     * 更新分类下标
     *
     * @param _description
     */
    fun updateDescription(_description: String) {
        description.value = _description
    }

    fun updateStatus(_state: Boolean) {
        addSuccess.value = _state
    }
}

class AddSheetViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(AddSheetViewModel::class.java)) {
            return AddSheetViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}