package com.aman.menuorderapp

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.aman.menuorderapp.adapters.MenuItemAdapter
import com.aman.menuorderapp.databinding.DialogMenuItemBinding
import com.aman.menuorderapp.databinding.FragmentMenuBinding
import com.aman.menuorderapp.interfaces.ClickType
import com.aman.menuorderapp.interfaces.MenuItemClick
import com.aman.menuorderapp.models.MenuItem

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var adapter: MenuItemAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MenuItemAdapter(mainActivity)
        binding.rvMenuItem.layoutManager = LinearLayoutManager(mainActivity)
        binding.rvMenuItem.adapter = adapter
        adapter.updateList(mainActivity.menuItem)
        adapter.setOnClickListener(object :MenuItemClick{
            override fun itemClicked(position: Int, type: ClickType) {
                when(type){
                    ClickType.edit->{
                        showDialog(mainActivity.menuItem[position], position)

                    }
                    ClickType.delete->{
                        AlertDialog.Builder(mainActivity).apply {
                        setTitle(resources.getString(R.string.delete_item))
                        setMessage(resources.getString(R.string.delete_item_msg))
                        setPositiveButton(resources.getString(R.string.yes)){_,_->
                            mainActivity.menuItem.removeAt(position)
                            adapter.updateList(mainActivity.menuItem)
                        }
                        setNegativeButton(resources.getString(R.string.no)){_,_->}
                            show()
                        }
                    }
                }
            }
        })
        binding.fab.setOnClickListener {
            showDialog()
        }
    }

    fun showDialog(menuItem: MenuItem = MenuItem(), position: Int = -1){
        var dialogBinding = DialogMenuItemBinding.inflate(layoutInflater)
        var dialog = Dialog(mainActivity)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.menuItem = menuItem
        if(position<0){
            dialogBinding.btnAdd.setText(mainActivity.resources.getString(R.string.add))
        }
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialogBinding.etItemName.doOnTextChanged { text, start, before, count ->
            if((text?.length?:0)>0){
                dialogBinding.tilItemName.isErrorEnabled = false
            }
        }
        dialogBinding.etItemPrice.doOnTextChanged { text, start, before, count ->
            if((text?.length?:0)>0){
                dialogBinding.tilItemPrice.isErrorEnabled = false
            }
        }
        dialogBinding.btnAdd.setOnClickListener {
            if(dialogBinding.etItemName.text.isNullOrEmpty()){
                dialogBinding.tilItemName.error = mainActivity.resources.getString(R.string.enter_menu_item_name)
                dialogBinding.tilItemName.isErrorEnabled = true
                dialogBinding.etItemName.requestFocus()
                return@setOnClickListener
            }else if(dialogBinding.etItemPrice.text.isNullOrEmpty()){
                dialogBinding.tilItemPrice.isErrorEnabled = true
                dialogBinding.tilItemPrice.error = mainActivity.resources.getString(R.string.enter_menu_item_price)
                dialogBinding.etItemPrice.requestFocus()
                return@setOnClickListener
            }else if(position == -1){
                mainActivity.menuItem.add(MenuItem(dialogBinding.etItemName.text.toString().trim(),dialogBinding.etItemPrice.text.toString().trim().toDouble()))
            }else{
                mainActivity.menuItem.removeAt(position)
                mainActivity.menuItem.add(position, MenuItem(dialogBinding.etItemName.text.toString().trim(),dialogBinding.etItemPrice.text.toString().trim().toDouble()))
            }
            adapter.updateList(mainActivity.menuItem)

            dialog.dismiss()
        }
        dialog.show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}