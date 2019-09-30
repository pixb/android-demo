package com.pix.testcoordinatorlayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_text.view.*

class RcvAdapter : RecyclerView.Adapter<RcvAdapter.ViewHolder>() {
    var dataList:List<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.layout_text,null,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataList?.size?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_content.setText(dataList?.get(position))
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}

