package top.roy1994.bilimusic.ui.navigation


sealed class Screens(val route: String) {
    object Main: Screens("Main_Screen")
    object Detail: Screens("Detail_Sheet_Screen")
}