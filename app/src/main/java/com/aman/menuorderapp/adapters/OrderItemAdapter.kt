package com.aman.menuorderapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aman.menuorderapp.databinding.LayoutMenuItemBinding
import com.aman.menuorderapp.databinding.LayoutOrderItemBinding
import com.aman.menuorderapp.interfaces.MenuItemClick
import com.aman.menuorderapp.interfaces.OrderItemClick
import com.aman.menuorderapp.models.MenuItem

class OrderItemAdapter(var context: Context, var clickListener: OrderItemClick):RecyclerView.Adapter<OrderItemAdapter.ViewHolder>() {
    var list =  ArrayList<MenuItem>()
    inner class ViewHolder(var binding: LayoutOrderItemBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(menuItem: MenuItem,position: Int, clickListener : OrderItemClick){
            binding.data= menuItem
            binding.clickListener = clickListener
            binding.position = position
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutOrderItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position],position, clickListener)
    }

    override fun getItemCount(): Int = list.size

    fun updateList(list: ArrayList<MenuItem>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun clearList(){
        this.list.clear()
    }

}