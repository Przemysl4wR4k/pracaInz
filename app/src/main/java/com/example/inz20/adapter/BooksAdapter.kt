package com.example.inz20.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.inz20.R
import kotlinx.android.synthetic.main.item_book.view.*

data class BookInfo(
    val title: String,
    val description: String,
    @DrawableRes val image: Int,
)

class BooksAdapter(
    fragmentActivity: FragmentActivity,
    private val booksInfoSlides: List<BookInfo>,
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = booksInfoSlides.size

    override fun createFragment(position: Int) = BookSlideFragment.newInstance(
        booksInfoSlides[position],
    )
}