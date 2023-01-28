package com.o7solutions.imgrgbpicker.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.o7solutions.imgrgbpicker.R
import com.o7solutions.imgrgbpicker.interfaces.ClickInterface
import com.o7solutions.imgrgbpicker.model.ColorCode

class ColorCodeAdapter(var userList: ArrayList<ColorCode>, var clickInterface: ClickInterface) :
    RecyclerView.Adapter<ColorCodeAdapter.ViewHolder>(){

    inner class ViewHolder(var v : View): RecyclerView.ViewHolder(v) {
        var tvCode1: TextView = v.findViewById(R.id.tvCode1)
        var ivDelete: ImageView = v.findViewById(R.id.ivDelete)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.code_list,parent,false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var newList = userList[position]
        holder.tvCode1.text=newList.code1
        holder.ivDelete.setOnClickListener {
            clickInterface.deleteClick(newList,position)
       }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
     fun setData(colorCode: List<ColorCode>){
        this.userList= colorCode as ArrayList<ColorCode>
        notifyDataSetChanged()
    }
}