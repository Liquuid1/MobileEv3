package com.example.snkr_app.navigation

sealed class Route(val route: String) {
    data object Welcome : Route("welcome")
    data object MenuShell : Route("menu_shell") // contenedor con drawer
    data object Home : Route("home")
    data object Productos : Route("productos")
    data object Newsletter : Route("newsletter")

    //detalle con argumento
    data object ProductosDetail : Route("productos/detail/{id}") {
        fun build(id: String) = "productos/detail/$id"
    }

    data object AddZapatilla : Route("addZapatilla")

    data object AdminPanel: Route("adminPanel")

}