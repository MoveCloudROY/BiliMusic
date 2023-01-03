package top.roy1994.bilimusic.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import top.roy1994.bilimusic.data.objects.TopSelectBarCategory

class TopSelectViewModel: ViewModel() {
    // topSelectBar data
    val categories = mutableStateOf(
        listOf(
            TopSelectBarCategory("主页"),
            TopSelectBarCategory("歌曲"),
            TopSelectBarCategory("歌单"),
            TopSelectBarCategory("标签"),
            TopSelectBarCategory("艺术家"),
        )
    )

    var categoryIndex = mutableStateOf(0)
        private set

    /**
     * 更新分类下标
     *
     * @param index
     */
    fun updateCategoryIndex(index: Int) {
        categoryIndex.value = index
    }
}