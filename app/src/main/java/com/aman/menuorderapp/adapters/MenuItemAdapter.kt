package com.aman.menuorderapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aman.menuorderapp.databinding.LayoutMenuItemBinding
import com.aman.menuorderapp.interfaces.MenuItemClick
import com.aman.menuorderapp.models.MenuItem

class MenuItemAdapter(var context: Context):RecyclerView.Adapter<MenuItemAdapter.ViewHolder>() {
    var list =  ArrayList<MenuItem>()
    lateinit var clickListener: MenuItemClick
    inner class ViewHolder(var binding: LayoutMenuItemBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(menuItem: MenuItem,position: Int, clickListener : MenuItemClick){
            binding.data= menuItem
            binding.clickListener = clickListener
            binding.position = position
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutMenuItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position],position, clickListener)
    }

    override fun getItemCount(): Int = list.size

    fun updateList(list: ArrayList<MenuItem>){
        this.list.clear()
        this.list.addAll(list)
    }

    fun clearList(){
        this.list.clear()
    }

    fun setOnClickListener(click: MenuItemClick){
        clickListener = click
        notifyDataSetChanged()
    }
}