package com.moyoi.library_base.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * @Description BaseAdapter
 * @Author Lu
 * @Date 2021/6/21 11:41
 * @Version: 1.0
 */
abstract class BaseAdapter<DATA> : RecyclerView.Adapter<BaseAdapter.ViewBindHolder>() {

    abstract fun onCreateViewBinding(viewType: Int): (LayoutInflater, ViewGroup, Boolean) -> ViewBinding

    abstract fun onItemView(holder: ViewBindHolder, position: Int, item: DATA)

    private val ids: MutableList<Int> = ArrayList()
    private var datas: MutableList<DATA> = ArrayList()
    private var onItemClickListener: OnItemClickListener? = null
    private var onItemChildClickListener: OnItemChildClickListener? = null
    private var onItemLongClickListener: OnItemLongClickListener? = null

    fun addOnClickListener(id: Int) {
        ids.add(id)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    fun setOnItemChildClickListener(listener: OnItemChildClickListener) {
        onItemChildClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        onItemLongClickListener = listener
    }

    fun setNewData(newData: List<DATA>? = null) {
        datas.clear()
        if (newData != null) {
            datas.addAll(newData)
        }
        notifyDataSetChanged()
    }

    fun addData(newData: List<DATA>) {
        datas.addAll(newData)
        notifyItemRangeChanged(datas.size - newData.size, newData.size)
    }

    fun addData(index: Int, newData: List<DATA>) {
        datas.addAll(index, newData)
        notifyItemRangeChanged(index, newData.size)
    }

    fun addOneData(data: DATA) {
        if (!datas.contains(data))
            datas.add(data)
        notifyDataSetChanged()
    }

    fun removeData(position: Int) {
        if (position < 0 || position >= datas.size) return
        datas.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, datas.size - position)
    }

    fun clearData() {
        datas.clear()
        notifyDataSetChanged()
    }

    fun getData(): MutableList<DATA> {
        return datas
    }

    fun getItem(position: Int): DATA {
        return datas[position]
    }

    fun <T> contextToActivity(context: Context): T {
        return context as T
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindHolder {
        return ViewBindHolder(
            onCreateViewBinding(viewType).invoke(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewBindHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(holder, position)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener?.onItemLongClick(holder, position)
            return@setOnLongClickListener false
        }

        onItemChildClickListener?.let { listener ->
            ids.forEach { id ->
                holder.getView<View>(id)?.setOnClickListener {
                    listener.onItemChildClick(it, holder, position)
                }
            }
        }
        onItemView(holder, position, datas[position])
    }

    override fun getItemCount(): Int = datas.size

    class ViewBindHolder(var binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun <T : View> getView(id: Int): T? {
            return itemView.findViewById(id)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(holder: ViewBindHolder, position: Int)
    }

    interface OnItemChildClickListener {
        fun onItemChildClick(view: View, holder: ViewBindHolder, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(holder: ViewBindHolder, position: Int)
    }
}

