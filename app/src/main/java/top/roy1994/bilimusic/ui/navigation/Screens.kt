package top.roy1994.bilimusic.ui.navigation

import android.app.Dialog


sealed class Screens(val route: String) {
    object Main: Screens("Main_Screen")
    object SheetDetail: Screens("Detail_Sheet_Screen")
    object ArtistDetail: Screens("Detail_Artist_Screen")
    object MusicConfig: Screens("Config_Music")

}