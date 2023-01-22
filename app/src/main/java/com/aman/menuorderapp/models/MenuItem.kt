package com.aman.menuorderapp.models

data class MenuItem(
    var menuItem: String?= "",
    var price: Double?= 0.0,
    var quantity: Int= 1,


) {
    override fun toString(): String {
        return "$menuItem"
    }
}
