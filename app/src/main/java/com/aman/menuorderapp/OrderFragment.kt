package com.aman.menuorderapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aman.menuorderapp.adapters.OrderItemAdapter
import com.aman.menuorderapp.databinding.FragmentOrderBinding
import com.aman.menuorderapp.interfaces.ClickType
import com.aman.menuorderapp.interfaces.OrderItemClick
import com.aman.menuorderapp.interfaces.QtyType
import com.aman.menuorderapp.models.MenuItem

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private lateinit var menuAdapter: ArrayAdapter<MenuItem>
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var mainActivity: MainActivity
    var orderList = arrayListOf<MenuItem>()
    lateinit var orderItemAdapter: OrderItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuAdapter = ArrayAdapter(mainActivity, android.R.layout.simple_list_item_1, mainActivity.menuItem)
        binding.spinnerMenu.adapter = menuAdapter
        binding.spinnerMenu.setOnItemSelectedListener(object: OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        })


        orderItemAdapter = OrderItemAdapter(mainActivity, object : OrderItemClick{
            override fun OrderItemClicked(position: Int, type: QtyType) {
                when(type){
                    QtyType.add->{
                        orderList[position].quantity = orderList[position].quantity+1
                    }
                    QtyType.minus->{
                        if(orderList[position].quantity>1){
                            orderList[position].quantity = orderList[position].quantity-1
                        }else{
                            orderList.removeAt(position)
                        }
                    }
                }
                orderItemAdapter.updateList(orderList)


            }
        })
        binding.rvOrder.apply {
            layoutManager = LinearLayoutManager(mainActivity)
            adapter = orderItemAdapter
        }
        binding.btnAdd.setOnClickListener {
            if(mainActivity.menuItem.isNullOrEmpty()){
                Toast.makeText(mainActivity, resources.getString(R.string.add_menu_items), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            orderList.add(mainActivity.menuItem[binding.spinnerMenu.selectedItemPosition])
            orderItemAdapter.updateList(orderList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}