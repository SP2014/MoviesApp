package com.mustafa.movieapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.mustafa.movieapp.R
import com.mustafa.movieapp.databinding.ItemPersonSearchBinding
import com.mustafa.movieapp.models.entity.Person
import com.mustafa.movieapp.view.ui.common.AppExecutors
import com.mustafa.movieapp.view.ui.common.DataBoundListAdapter

class PeopleSearchListAdapter(
    appExecutors: AppExecutors,
    private val dataBindingComponent: DataBindingComponent,
    private val movieOnClickCallback: ((Person) -> Unit)?
) : DataBoundListAdapter<Person, ItemPersonSearchBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == (newItem)
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ItemPersonSearchBinding {
        val binding = DataBindingUtil.inflate<ItemPersonSearchBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_person_search,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.person?.let {
                movieOnClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ItemPersonSearchBinding, item: Person) {
        binding.person = item
    }
}