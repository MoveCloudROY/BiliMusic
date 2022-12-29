package top.roy1994.bilimusic.viewmodel


import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import top.roy1994.bilimusic.data.struct.Music

class AddMusicViewModel: ViewModel() {
    var bvid = mutableStateOf("")
    var part = mutableStateOf("1")
    var name = mutableStateOf("")
    var sheet = mutableStateOf("默认歌单")

    fun addMusic() {

    }

    /**
     * 更新分类下标
     *
     * @param bvid
     */
    fun updateBvid(_bvid: String) {
        bvid.value = _bvid
    }
    /**
     * 更新分类下标
     *
     * @param part
     */
    fun updatePart(_part: String) {
        part.value = _part
    }
    /**
     * 更新分类下标
     *
     * @param name
     */
    fun updateName(_name: String) {
        name.value = _name
    }
    /**
     * 更新分类下标
     *
     * @param sheet
     */
    fun updateSheet(_sheet: String) {
        sheet.value = _sheet
    }
}