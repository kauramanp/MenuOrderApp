package com.aman.menuorderapp.interfaces

import com.aman.menuorderapp.models.MenuItem

interface ClickInterfaces {
}
interface MenuItemClick {
    fun itemClicked(position:Int, type:ClickType)
}
enum class ClickType{
    edit, delete
}