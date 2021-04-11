package com.example.ui.recyclerview

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class BaseRecyclerView<T: BaseRecyclerView.Model, VH: BaseRecyclerView.BaseViewHolder<*>>: RecyclerView.Adapter<VH>() {

    protected val items: MutableList<T> = mutableListOf()

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long = items[position].id.hashCode().toLong()

    fun swap(data: List<T>) {
        val diffUtilCallback = object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = items[oldItemPosition]
                val newItem = data[newItemPosition]
                return oldItem.id == newItem.id
            }

            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = data.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                items[oldItemPosition] == data[newItemPosition]

        }

        val result = DiffUtil.calculateDiff(diffUtilCallback)
        items.clear()
        items.addAll(data)

        result.dispatchUpdatesTo(this)

    }

    abstract class BaseViewHolder<T>(view: View): RecyclerView.ViewHolder(view) {
        protected val context: Context get() = itemView.context
        private var item: T? = null

        fun bind(item: T) {
            this.item = item
        }

        protected fun <T> viewBindings(binder: (View) -> T) = ViewHolderBindingReadProperty(binder)

        protected class ViewHolderBindingReadProperty<T>(val binder: (View) -> T): ReadOnlyProperty<RecyclerView.ViewHolder, T> {
            override fun getValue(thisRef: RecyclerView.ViewHolder, property: KProperty<*>): T {
                return binder(thisRef.itemView)
            }

        }
    }

    interface Model {
        val id: String
    }
}