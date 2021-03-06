package com.mustafa.movieapp.view.adapter

import android.view.View

import com.mustafa.movieapp.R
import com.mustafa.movieapp.models.Resource
import com.mustafa.movieapp.models.Review
import com.mustafa.movieapp.view.viewholder.BaseAdapter
import com.mustafa.movieapp.view.viewholder.BaseViewHolder
import com.mustafa.movieapp.view.viewholder.ReviewListViewHolder
import com.mustafa.movieapp.view.viewholder.SectionRow

/**
 * https://github.com/skydoves/TheMovies
 */
class ReviewListAdapter : BaseAdapter() {

    init {
        addSection(ArrayList<Review>())
    }

    fun addReviewList(resource: Resource<List<Review>>) {
        resource.data?.let {
            sections()[0].addAll(it)
        }
        notifyDataSetChanged()
    }

    override fun layout(sectionRow: SectionRow): Int {
        return R.layout.item_review
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return ReviewListViewHolder(view)
    }
}
