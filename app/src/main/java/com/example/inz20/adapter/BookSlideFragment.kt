package com.example.inz20.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.inz20.R
import kotlinx.android.synthetic.main.fragment_book_slide.view.*

class BookSlideFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
            container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_book_slide,
        container,
        false
    ).apply {
        val (title, description, image) = arguments?.run {
            listOf(
                getString(SLIDE_TITLE),
                getString(SLIDE_DESC),
                getInt(SLIDE_IMAGE)
            )
        } ?: List(ARGUMENTS_SIZE) { "" }

        Log.e("_TEST", title.toString())
        Log.e("_TEST", description.toString())
        Log.e("_TEST", image.toString())
        labelBookSlideTitle.text = title as String
        labelBookSlideDesc.text = description as String
        imageBookSlide.setImageResource(image as Int)

    }

    companion object {
        private const val ARGUMENTS_SIZE = 3
        const val SLIDE_TITLE = "slide_title"
        const val SLIDE_DESC = "slide_desc"
        const val SLIDE_IMAGE = "slide_image"

        @JvmStatic
        fun newInstance(
            bookInfo: BookInfo,
        ) = BookSlideFragment().apply {

            arguments = Bundle().apply {
                putString(SLIDE_TITLE, bookInfo.title)
                putString(SLIDE_DESC, bookInfo.description)
                putInt(SLIDE_IMAGE, bookInfo.image)
            }
        }
    }
}