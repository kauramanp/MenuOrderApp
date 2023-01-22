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
enum class QtyType{
   add, minus
}

interface OrderItemClick {
    fun OrderItemClicked(position:Int, type:QtyType)
}